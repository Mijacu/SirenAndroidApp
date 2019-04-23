package edu.utep.cs.sirenandroidapp.Model;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.video.BackgroundSubtractorMOG2;
import org.opencv.video.Video;


public final class BackgroundSubtractorDetector extends BaseDetector implements IDetector {

    //private static final String TAG = makeLogTag(BackgroundSubtractorDetector.class);

    private static final double LEARNING_RATE = 0.1;

    private static final int MIXTURES = 4;

    private static final int HISTORY = 20;

    private static final double BACKGROUND_RATIO = 0.8;

    // ring image buffer
    private Mat buf = null;
    private BackgroundSubtractorMOG2 bg;
    public Mat fgMask= new Mat();
    private List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
    private Mat hierarchy = new Mat();
    private List<Integer> remove=new ArrayList<Integer>();
    public BackgroundSubtractorDetector() {
        //bg = new BackgroundSubtractor(HISTORY, MIXTURES, BACKGROUND_RATIO);
        bg = Video.createBackgroundSubtractorMOG2(HISTORY,16,true);
        bg.setBackgroundRatio(BACKGROUND_RATIO);
        bg.setHistory(HISTORY);
        bg.setNMixtures(MIXTURES);
    }

    public BackgroundSubtractorDetector(int backgroundRatio) {
        //bg = new BackgroundSubtractorMOG(HISTORY, MIXTURES, (backgroundRatio / 100.0));
        bg = Video.createBackgroundSubtractorMOG2(HISTORY,16,true);
        bg.setBackgroundRatio((backgroundRatio / 100.0));
        bg.setHistory(HISTORY);
        bg.setNMixtures(MIXTURES);
    }

    public void setThreshold(int backgroundRatio) {
        bg.setBackgroundRatio(backgroundRatio / 100.0);
    }

    @Override
    public Mat detect(Mat source) {
        try {
            // get current frame size
            Size size = source.size();

            // allocate images at the beginning or
            // reallocate them if the frame size is changed
            if (buf == null || buf.width() != size.width || buf.height() != size.height) {
                if (buf == null) {
                    buf = new Mat(size, CvType.CV_8UC4);
                    buf = Mat.zeros(size, CvType.CV_8UC4);
                }
            }

            // convert frame to gray scale
            Imgproc.cvtColor(source, buf, Imgproc.COLOR_RGB2GRAY);
            Imgproc.GaussianBlur(buf,buf, new Size(21, 21), 0);

            bg.apply(buf, fgMask, LEARNING_RATE); //apply() exports a gray image by definition

            Mat element1 = Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, new  Size(2*2 + 1, 2*2+1));
            Mat element2 = Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, new  Size(2*3 + 1, 2*3+1));
            Imgproc.erode(fgMask, fgMask, element1);
            Imgproc.dilate(fgMask, fgMask, element2);
            //Imgproc.cvtColor(fgMask, silh, Imgproc.COLOR_GRAY2RGBA);

            contours.clear();
            remove.clear();
            Imgproc.findContours(fgMask, contours, hierarchy, Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);
            for(int i=0;i<contours.size();i++){
                double area = Imgproc.contourArea(contours.get(i));
                if(area < 2000){
                    remove.add(i);
                }
            }
            /**
            for(Integer i:remove){
                double area = Imgproc.contourArea(contours.get(i));
                Log.d("Msgf",Double.toString(area));
                contours.remove(i);
            }**/
            Imgproc.drawContours(source, contours, -1, contourColor, contourThickness);
            if (contours.size() > 0) {
                targetDetected = true;
            } else {
                targetDetected = false;
            }

            return source;
        } catch (Exception e) {
            //LOGE(TAG, "[detect] Error processing frame", e);
            Log.d("Msgf","Error processing frame");

            return source;
        }
    }
}
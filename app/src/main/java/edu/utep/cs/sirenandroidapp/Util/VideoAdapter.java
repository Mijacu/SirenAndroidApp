package edu.utep.cs.sirenandroidapp.Util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.VideoView;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import edu.utep.cs.sirenandroidapp.Model.CameraHelper;
import edu.utep.cs.sirenandroidapp.Model.Video;
import edu.utep.cs.sirenandroidapp.R;

import static android.support.constraint.Constraints.TAG;

public class VideoAdapter extends ArrayAdapter <Video> {


    private Context mContext;
    private List<Video> mVideos;
    private TextView videoName;
    private Button delete;


    public VideoAdapter(@NonNull Context context, @SuppressLint("SupportAnnotationUsage") @LayoutRes List<Video> object) {
        super(context, 0 ,object);
        mContext = context;
        mVideos = object;

    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.activity_listview, null);
            holder = new ViewHolder();
            holder.videoView = (VideoView) convertView.findViewById(R.id.videoView);
            videoName=(TextView) convertView.findViewById(R.id.textView);
            delete=(Button)convertView.findViewById(R.id.delete);



            convertView.setTag(holder);
        }
        else {

            holder = (ViewHolder) convertView.getTag();

        }
        /***get clicked view and play video url at this position**/
        try {
            Video video = mVideos.get(position);
            //play video using android api, when video view is clicked.
            String path = video.getPath(); // your videoName
            System.out.println("=========================: "+path);
           Uri videoUri = Uri.parse(path);
           // mContext.getContentResolver().delete(Uri.parse(url), null, null);
            holder.videoView.setVideoURI(videoUri);
            videoName.setText("ID : "+video.getDate());

            delete.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                mVideos.remove(position);
                notifyDataSetChanged();

                    String path="/storage/emulated/0/Pictures/SirenAppVideos/VID_20190502_220051.mp4";
                    path="content://"+path;
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.parse(path), "video/*");
                    mContext.startActivity(intent);

                }
            });

            holder.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.setLooping(true);
                    mp.setVolume(0,0);
                    holder.videoView.start();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertView;
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        menu.add(0, v.getId(), 0, "Delete");
    }


    public static class ViewHolder {
        VideoView videoView;

    }

    public void deleteVideo(String path){

        File file = new File(path);
        boolean deleted = file.delete();
        System.out.println("true or false: "+deleted);
        //mContext.getContentResolver().delete(Uri.parse(path), null, null);

    }




     /* String path="/storage/emulated/0/Pictures/SirenAppVideos/VID_20190502_220051.mp4";
                path="content://"+path;
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setDataAndType(Uri.parse(path), "video/*");
                        startActivity(intent);*/



}

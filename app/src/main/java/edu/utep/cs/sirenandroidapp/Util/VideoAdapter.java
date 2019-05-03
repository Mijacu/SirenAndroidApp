package edu.utep.cs.sirenandroidapp.Util;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.VideoView;

import java.util.List;

import edu.utep.cs.sirenandroidapp.Model.CameraHelper;
import edu.utep.cs.sirenandroidapp.Model.Video;
import edu.utep.cs.sirenandroidapp.R;

public class VideoAdapter extends ArrayAdapter <Video> {
    private static final String TAG ="SirenApp";

    private Context mContext;
    private List<Video> mVideos;
    private TextView videoName;
    private DatabaseHelper databaseHelper;
    private AdapterListener adaptarListener;

    public interface AdapterListener{
        void notifyAdapter();
    }
    public VideoAdapter( Context context, List<Video> object) {
        super(context, R.layout.activity_listview ,object);
        mContext = context;
        mVideos = object;
        databaseHelper=new DatabaseHelper(mContext);
    }

    public void setListener(AdapterListener adaptarListener){
        this.adaptarListener=adaptarListener;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.activity_listview, parent,false);
            holder = new ViewHolder();
            holder.videoView = (VideoView) convertView.findViewById(R.id.videoView);
            holder.videoView.setOnClickListener((view)->{
                Video video=databaseHelper.videosList().get(position);
                databaseHelper.removeVideo(video.getId());
                adaptarListener.notifyAdapter();
                Log.d(TAG,"Remove Video");
            });
            videoName=(TextView) convertView.findViewById(R.id.textView);
            convertView.setTag(holder);
        }
        else {

            holder = (ViewHolder) convertView.getTag();

        }

        /***get clicked view and play video url at this position**/
        try {
            Video video = mVideos.get(position);
            //play video using android api, when video view is clicked.
            String url = video.getPath(); // your videoName
            Uri videoUri = Uri.parse(url);
           // Uri videoUri= Uri.parse(Environment.getExternalStorageDirectory().getPath()+"/media/1.mp4" );
            holder.videoView.setVideoURI(videoUri);
                videoName.setText("Date : "+video.getDate());
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

    public static class ViewHolder {
        VideoView videoView;
    }

    public void deleteVideo(String path){
        mContext.getContentResolver().delete(Uri.parse(path), null, null);

    }
}

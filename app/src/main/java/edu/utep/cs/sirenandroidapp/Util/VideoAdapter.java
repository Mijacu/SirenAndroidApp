package edu.utep.cs.sirenandroidapp.Util;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.VideoView;

import java.util.List;

import edu.utep.cs.sirenandroidapp.Model.Video;
import edu.utep.cs.sirenandroidapp.R;

public class VideoAdapter extends ArrayAdapter <Video> {


    private Context mContext;
    private List<Video> mVideos;
    private TextView videoName;


    public VideoAdapter( Context context, List<Video> object) {
        super(context, R.layout.activity_listview ,object);
        mContext = context;
        mVideos = object;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.activity_listview, null);
            holder = new ViewHolder();
            holder.videoView = (VideoView) convertView.findViewById(R.id.videoView);
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
            String url = video.getName(); // your videoName
           Uri videoUri = Uri.parse(url);
           // Uri videoUri= Uri.parse(Environment.getExternalStorageDirectory().getPath()+"/media/1.mp4" );
            holder.videoView.setVideoURI(videoUri);
                videoName.setText("Date : "+video.getDate());
            holder.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.setLooping(true);
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
        mContext.getContentResolver().delete(Uri.parse(path), null, null);

    }








}

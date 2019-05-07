package edu.utep.cs.sirenandroidapp.Util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.provider.Settings;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import edu.utep.cs.sirenandroidapp.Model.CameraHelper;
import edu.utep.cs.sirenandroidapp.Model.Video;
import edu.utep.cs.sirenandroidapp.R;
import edu.utep.cs.sirenandroidapp.SentryModeActivity;

import static android.support.constraint.Constraints.TAG;

public class VideoAdapter extends ArrayAdapter <Video> {

    private static final String TAG ="SirenApp";

    private Context mContext;
    private List<Video> mVideos;
    private TextView videoName;
    private ImageView delete;
    private ImageView play;
    private DatabaseHelper dbHelper;
    private Activity activity;
    private NotificationDialogFragment notificationDialog;
    private Video video;

    //Dialog fragments
    private void showItemDialog(int title,int pos) {

            if(title==R.string.delete_question ){
            notificationDialog=new NotificationDialogFragment().newInstance(title);
            notificationDialog.setNotificationDialogListener(new NotificationDialogFragment.NotificationDialogListener() {
                @Override
                public void onDeleteDialogPositiveClick() {
                    dbHelper.removeVideo(video.getId());
                    Intent intent=new Intent();
                    intent.putExtra("message","keep video list");
                    activity.setResult(activity.RESULT_OK, intent);
                    activity.finish();//finishing activity
                }

            });
            notificationDialog.show(((FragmentActivity)activity).getSupportFragmentManager(), "NotificationDialogFragment");
        }
    }

    public VideoAdapter(@NonNull Context context, @SuppressLint("SupportAnnotationUsage") @LayoutRes List<Video> object) {
        super(context, 0 ,object);
        mContext = context;
        mVideos = object;
        dbHelper=new DatabaseHelper(mContext);
    }

    public void setActivity(Activity activity){
        this.activity=activity;
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
            play=convertView.findViewById(R.id.play);
            delete=convertView.findViewById(R.id.delete);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        /***get clicked view and play video url at this position**/
        try {
            Video video = mVideos.get(position);
            this.video=video;
            //play video using android api, when video view is clicked.
            String path = video.getPath(); // your videoName
            Uri videoUri = Uri.parse(path);
            // mContext.getContentResolver().delete(Uri.parse(url), null, null);
            holder.videoView.setVideoURI(videoUri);
            videoName.setText(video.getDate());
            play.setOnClickListener((view)->{
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.parse(path), "video/*");
                activity.startActivity(intent);
            });

            delete.setOnClickListener((view)->{
                Log.d(TAG, "Position= "+position);
                showItemDialog(R.string.delete_question,position);
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

    public static class ViewHolder {
        VideoView videoView;
    }
}


     /* String path="/storage/emulated/0/Pictures/SirenAppVideos/VID_20190502_220051.mp4";
                path="content://"+path;
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setDataAndType(Uri.parse(path), "video/*");
                        startActivity(intent);*/

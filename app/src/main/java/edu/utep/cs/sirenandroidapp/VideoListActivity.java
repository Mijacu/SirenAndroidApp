package edu.utep.cs.sirenandroidapp;

import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import edu.utep.cs.sirenandroidapp.Model.CameraHelper;
import edu.utep.cs.sirenandroidapp.Model.Video;
import edu.utep.cs.sirenandroidapp.Util.DatabaseHelper;
import edu.utep.cs.sirenandroidapp.Util.VideoAdapter;

public class VideoListActivity extends AppCompatActivity {


    private ListView mVideosListView;
    private List<Video> mVideosList = new ArrayList<>();
    private VideoAdapter mVideoAdapter;
    private DatabaseHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_list);
        mVideosListView = (ListView) findViewById(R.id.video_list);
        dbHelper=new DatabaseHelper(this);
        mVideosList=dbHelper.videosList();

        /***populate video list to adapter**/
        mVideoAdapter = new VideoAdapter(this, mVideosList);
        mVideosListView.setAdapter(mVideoAdapter);
        registerForContextMenu(mVideosListView);
    }
}

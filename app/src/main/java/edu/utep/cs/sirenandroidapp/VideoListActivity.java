package edu.utep.cs.sirenandroidapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class VideoListActivity extends AppCompatActivity {


    String[] videoArray = {"Date:4/13/2019 2:32 PM","Date:4/14/2019 1:00 PM","Date:4/20/2019 4:20 PM","Date:4/21/2019 9:55 PM",
            "Date:4/23/2019 8:00 AM","Date:4/24/2019 2:26 PM"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_list);
        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                R.layout.activity_listview, videoArray);

        ListView listView = (ListView) findViewById(R.id.video_list);
        listView.setAdapter(adapter);
    }
}

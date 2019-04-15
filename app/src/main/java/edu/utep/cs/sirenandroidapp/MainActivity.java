package edu.utep.cs.sirenandroidapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.opencv.android.OpenCVLoader;

public class MainActivity extends AppCompatActivity {

    Button sentryButton;
    Button videosButton;
    Button settingsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (!OpenCVLoader.initDebug()) {
            Log.d("Msgf", "  OpenCVLoader.initDebug(), not working.");
        } else {
            Log.d("Msgf", "  OpenCVLoader.initDebug(), working.");
        }

        sentryButton = (Button) findViewById(R.id.sentryButton);
        videosButton = (Button) findViewById(R.id.videosButton);
        settingsButton = (Button) findViewById(R.id.settingsButton);

        sentryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),SentryModeActivity.class);
                startActivity(i);
            }
        });
        videosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent i=new Intent("edu.utep.cs.sirenandroidapp.VideosActivity");
                //startActivity(i);
            }
        });
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent i=new Intent("edu.utep.cs.sirenandroidapp.SettingsActivity");
                //startActivity(i);
            }
        });
    }
}

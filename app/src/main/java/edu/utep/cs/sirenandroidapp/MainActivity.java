package edu.utep.cs.sirenandroidapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.preference.PreferenceManager;

import org.opencv.android.OpenCVLoader;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private static final String TAG ="SirenApp";

    private Button sentryButton;
    private Button videosButton;
    private Button settingsButton;
    private TextView userWelcome;
    private SharedPreferences prefs;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (!OpenCVLoader.initDebug()) {
            Log.d(TAG, "  OpenCVLoader.initDebug(), not working.");
        } else {
            Log.d(TAG, "  OpenCVLoader.initDebug(), working.");
        }
        userWelcome=findViewById(R.id.userName);
        sentryButton = (Button) findViewById(R.id.sentryButton);
        videosButton = (Button) findViewById(R.id.videosButton);
        settingsButton = (Button) findViewById(R.id.settingsButton);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        sentryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),SentryModeActivity.class);
                startActivityForResult(i, 1); // 1: request code


            }
        });
        videosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),VideoListActivity.class);
                startActivityForResult(i,1);
            }
        });
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),SirenSettings.class);
                startActivity(i);
            }
        });
    }

    public void onResume() {
        super.onResume();
        userName=prefs.getString("userNameId","");
        if(!userName.equals("")){userWelcome.setText("Welcome "+userName);}
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent result) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            String msg=result.getStringExtra("message");
            Log.d(TAG,msg);
            if(msg.equals("keep sentry")){
                Intent i=new Intent(getApplicationContext(),SentryModeActivity.class);
                startActivityForResult(i, 1); // 1: request code
            }else if(msg.equals("keep video list")){
                Intent i=new Intent(getApplicationContext(),VideoListActivity.class);
                startActivityForResult(i, 1); // 1: request code
            }
        }

    }
}

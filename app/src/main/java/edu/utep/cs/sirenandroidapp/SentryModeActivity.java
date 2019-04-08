package edu.utep.cs.sirenandroidapp;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class SentryModeActivity extends Activity {

    boolean motionDetected=true;
    final Context context = this;
    Camera camera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sentry_activity);



        if(motionDetected==true){
            //start recording until motion has stopped for 10 seconds
            // show activity buttons and put screen to sleep after 10 seconds of no motion detected
            //camera.
        }

        Button doorBell=(Button)findViewById(R.id.doorbell);
        Button disarm=(Button)findViewById(R.id.disarm);


        doorBell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dispatchTakeVideoIntent();
            //send notification or email about doorbell event to user
            }
        });

        disarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchDialog();
            }
        });
    }

    static final int REQUEST_VIDEO_CAPTURE= 2;

    private void dispatchTakeVideoIntent() {
        System.out.println("recording");
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

        if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takeVideoIntent,REQUEST_VIDEO_CAPTURE);
        }
    }

    private void launchDialog(){
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.number_pad);
        dialog.show();
        EditText pin=(EditText)findViewById(R.id.secret_pin);

    }



}

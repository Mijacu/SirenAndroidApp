package edu.utep.cs.sirenandroidapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class SirenSettings extends AppCompatActivity {

    EditText name;
    EditText email;
    EditText password;
    EditText recordTime;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_siren_settings);

        name.findViewById(R.id.name);
        email.findViewById(R.id.email);
        password.findViewById(R.id.password);
        recordTime.findViewById(R.id.recorctime);



    }



}

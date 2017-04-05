package com.example.epickpc.epickbets2;


import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;


public class ActionsActivity extends AppCompatActivity {
    TextView tnombre;
    TextView tmovil;
    TextView tv;

    //TextView tdeviceId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actions);

        tnombre = (TextView) findViewById(R.id.tnombre);
        tnombre.setText("Hola: " + getIntent().getExtras().getString("username"));

        tmovil = (TextView) findViewById(R.id.tmovil);
        tmovil.setText("Password: " + getIntent().getExtras().getString("password"));


        String id = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        //displaying id in textview

        tv = (TextView) findViewById(R.id.tdeviceId);
        tv.setText("Token ID: "+id);

        /*tdeviceId = (TextView) findViewById(R.id.tdeviceId);
        tdeviceId.setText("Token ID: " + getIntent().getExtras().getString("device"));
        */

    }
}
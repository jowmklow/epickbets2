package com.example.epickpc.epickbets2;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;


public class ActionsActivity extends AppCompatActivity{
    TextView tnombre;
    TextView tmovil;
    Boolean pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actions);

    tnombre = (TextView) findViewById(R.id.tnombre);
        tnombre.setText(getIntent().getExtras().getString("username"));
    tmovil = (TextView) findViewById(R.id.tmovil);



    }
    public void cambiaTexto(View v){

        tnombre.setText("Username= ");
        tmovil.setText("Device ID= ");
    }
}
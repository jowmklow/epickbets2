package com.example.epickpc.epickbets2;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class ActionsActivity extends AppCompatActivity{
    TextView texto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actions);
    texto = (TextView) findViewById(R.id.texto);
    }

    public void Cambia_texto(View v){
        texto.setText("Username= ");
    }

}
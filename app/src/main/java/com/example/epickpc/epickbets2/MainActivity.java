package com.example.epickpc.epickbets2;

import android.app.Activity;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements LoginListener {

    @BindView(R.id.entra_nom_inici)
    EditText entraNom;
    @BindView(R.id.entra_contrasenya_inici)
    EditText entraContra;
    @BindView(R.id.inicia_sessio)
    Button iniciaSessio;
    @BindView(R.id.imagenLogo)
    ImageView imagenLogo;
    Activity c;
    MyAsyncTask m;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        c = this;

        ConnectivityManager cManager = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo nInfo = cManager.getActiveNetworkInfo();

        if (nInfo != null && nInfo.isConnected()) {
            Snackbar.make(entraContra, "Internet connected", Snackbar.LENGTH_LONG).show();
        } else {
            Snackbar.make(entraContra, "Internet disconnected", Snackbar.LENGTH_LONG).show();
            }
        //servidor test
    }
    @OnClick({R.id.inicia_sessio, R.id.imagenLogo})
    public void onViewClicked(View view) {
        Intent i;
        switch (view.getId()) {
            case R.id.inicia_sessio:
                String deviceId = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
                m = new MyAsyncTask(entraNom.getText().toString(),entraContra.getText().toString(),deviceId,this);
                        /*Thread.start();
                        while (Thread.isAlive()) {
                        }
                        if (error) {
                            Toast.makeText(MainActivity.this, "Combinación Errónea", Toast.LENGTH_LONG).show();
                        }*/
                     m.run();
                break;
            case R.id.imagenLogo:
                i = new Intent(MainActivity.this, MainActivity.class);
                startActivity(i);
                finish();
                break;
        }

    }

    @Override
    public void ok() {
        Intent i = new Intent(this, ActionsActivity.class);
        startActivity(i);
    }
    @Override
    public void wrong() {
        Snackbar.make(entraContra, "Valores erroneos", Snackbar.LENGTH_LONG).show();
    }
}

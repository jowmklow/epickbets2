package com.example.epickpc.epickbets2;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.sql.Connection;
import javax.net.ssl.HttpsURLConnection;
import org.json.*;

public class MainActivity extends AppCompatActivity{

    private Button registra;
    private EditText entraNom;
    private EditText entraContra;
    private Button inicia;
    boolean error = false;
    HttpURLConnection urlConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ConnectivityManager cManager = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo nInfo = cManager.getActiveNetworkInfo();
        if (nInfo != null && nInfo.isConnected()) {
            Toast.makeText(this, "Internet conectado", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Internet desconectado", Toast.LENGTH_LONG).show();
            Intent offline = new Intent(this, ActionsActivity.class);
            startActivity(offline);
            finish();
        }
        //servidor test
        entraNom = (EditText) findViewById(R.id.entra_nom_inici);
        entraContra = (EditText) findViewById(R.id.entra_contrasenya_inici);
        inicia = (Button) findViewById(R.id.inicia_sessio);



        registra = (Button) findViewById(R.id.registra);
        registra.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Intent i = new Intent(MainActivity.this, RegistraActivity.class);
                                            startActivity(i);
                                            finish();
                                        }
                                    } //boton de registrar
        );
        inicia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqlThread.start();
                while (sqlThread.isAlive()) {
                }
                if (error) {
                Toast.makeText(MainActivity.this, "Combinación Errónea", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
    Thread sqlThread = new Thread() {
        public void run() {

        /*
        Connectar a la base de dades d'usuaris per saber si ha posat bé el nom i la contrasenya
        */
            entraNom.getText().toString();
            entraContra.getText().toString();
            Connection conn = null;


            try {
                //Your server URL
                String url = "https://www.epickbets.com/apitest/user/login";
                URL obj = new URL(url);
                HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
                //add request header
                con.setRequestMethod("POST");
                con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
                //Request Parameters you want to send
                String urlParameters = "username=jow&password=joel1995";
                // Send post request
                con.setDoOutput(true);// Should be part of code only for .Net web-services else no need for PHP
                DataOutputStream wr = new DataOutputStream(con.getOutputStream());
                wr.writeBytes(urlParameters);
                wr.flush();
                wr.close();

                int responseCode = con.getResponseCode();
                System.out.println("\nSending 'POST' request to URL : " + url);
                System.out.println("Post parameters : " + urlParameters);
                System.out.println("Response Code : " + responseCode);

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                System.out.println("Response:"+ response.toString());

                JSONObject object = new JSONObject(response.toString());
                System.out.print(object.getBoolean("result"));
                System.out.println("\nResponse Code : " + responseCode);
                System.out.println(response);
                if((object.getBoolean("result"))==false){
                    System.out.println("Valores erroneos");
                }else{
                    System.out.println("Valores correctos");
                    Intent i = new Intent(MainActivity.this, ActionsActivity.class);
                    startActivity(i);

                }
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };
}

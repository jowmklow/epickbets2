package com.example.epickpc.epickbets2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.Connection;


import javax.net.ssl.HttpsURLConnection;

public class RegistraActivity extends AppCompatActivity {
    private EditText id_user;
    private EditText pass;

    private Button registra; //valores de pantalla registra

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registra);

        id_user = (EditText) findViewById(R.id.name);
        pass= (EditText) findViewById(R.id.pass);
        registra = (Button) findViewById(R.id.registra); //strings values R activitys

//introducimos el xml a nuestros datos para que almacene estos dentro de el.


        registra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqlThread.start();

                if (sqlThread.isAlive()) {
                    System.out.print("Correcte");
                } else {
                    System.out.print("Error");
                }
            }
        });
    }

    Thread sqlThread = new Thread() {
        public void run() {

            String name = id_user.getText().toString();
            String password = pass.getText().toString();

            String insert = "insert into user_id values('" + name + "', '" + password + "', '" + "DEFAULT" + "', '"  + "14/03/2017"+");";
            Connection conn = null;

        }
        private void sendPost() throws Exception {

            //Your server URL
            String url = "https://www.epickbets/apitest/login/user";
            URL obj = new URL(url);
            HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

            //add reuqest header
            con.setRequestMethod("POST");
            con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

            //Request Parameters you want to send
            String urlParameters = "=&username=jowmklow=&password=joel1995";

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

            //print result
            System.out.println(response.toString());

        }
    };
}
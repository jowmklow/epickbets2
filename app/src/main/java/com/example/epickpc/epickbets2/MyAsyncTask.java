package com.example.epickpc.epickbets2;

import android.os.StrictMode;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;


public class MyAsyncTask{
    private String nombre;
    private String pass;
    private String deviceId;
    private LoginListener l;

    public MyAsyncTask(String nombre, String pass, String d, LoginListener l) {
        this.nombre = nombre;
        this.pass = pass;
        this.deviceId = d;
        this.l = l;
    }

    public void run(){
        Thread.run();
    }

    Thread Thread = new Thread() {

        public void run() {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

        /*
        Connectar a la base de dades d'usuaris per saber si ha posat bé el nom i la contrasenya
        */

            try {
                //Your server URL
                String url = "https://www.epickbets.com/apitest/user/login";
                URL obj = new URL(url);
                HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
                //add request header
                con.setRequestMethod("POST");
                con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
                //Request Parameters you want to send
                String urlParameters = "username=" + nombre + "&password=" + pass;
                // Send post request
                con.setDoOutput(true);// Should be part of code only for .Net web-services else no need for PHP
                DataOutputStream wr = new DataOutputStream(con.getOutputStream());
                wr.writeBytes(urlParameters);
                wr.flush();
                wr.close();

                int responseCode = con.getResponseCode();
                Log.d("Valores introducidos:", "\nUser" + nombre + ", Password: " + pass + "");
                Log.d("", "\nSending 'POST' request to URL : " + url);
                Log.d("", "Post parameters : " + urlParameters);
                Log.d("", "Response Code : " + responseCode);

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));

                Log.e("", "\nResponse: _-_-_-_-_-_-_-_-_-_-_-_-_-_-" + deviceId);

                //TODO: In case the application requested permission for files permission, accept
                /*
                  if (ContextCompat.checkSelfPermission(c,
                        Manifest.permission.READ_PHONE_STATE)
                        != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(c,
                            Manifest.permission.READ_PHONE_STATE)) {
                    } else {
                        ActivityCompat.requestPermissions(c,
                                new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
                }
                TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
                telephonyManager.getDeviceId();


                Log.e("", "Response: _____________________________" + telephonyManager.getDeviceId());
                */


                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                JSONObject object = new JSONObject(response.toString());
                Log.d("", "" + object.getBoolean("result"));
                Log.d("", "\nResponse Code : " + responseCode);
                Log.d("", "" + response);

                if ((object.getBoolean("result")) == false) {
                    //Log.d("","Valores erroneos");
                    //Snackbar.make(, "Valores erroneos", Snackbar.LENGTH_LONG).show();
                    Log.e("Introduccion de", "Valores erroneos");
                    l.wrong();
                } else {
                    l.ok();
                    Log.d("", "Valores correctos");

                    //Intent i = new Intent(MyAsyncTask.this, ActionsActivity.class);
                    //startActivity(i);

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
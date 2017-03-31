package com.example.epickpc.epickbets2;

import java.io.DataOutputStream;
import java.net.URL;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import javax.net.ssl.HttpsURLConnection;


public class MyAsyncTask extends AsyncTask<URL, Void, String> {

    private HttpURLConnection urlConnection;
    private Context mContext;
    private ProgressDialog mDialog;

    // HTTP POST request
    private void sendPost() throws Exception {

        //Your server URL
        String url = "https://www.epickbets.com/apitest/user/login";
        URL obj = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

        //add reuqest header
        con.setRequestMethod("POST");
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        //Request Parameters you want to send
        String urlParameters = "?username=jow&password=joel1995";

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

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mDialog.setTitle(R.string.app_name);
        mDialog.setMessage("Retrieving data...");
        mDialog.show();
    }

    @Override
    protected String doInBackground(URL... params) {
        StringBuilder result = new StringBuilder();

        try {
            URL url = params[0];
//            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("10.0.0.1", 8080));
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoInput(true);
            urlConnection.setConnectTimeout(20 * 1000);
            urlConnection.setReadTimeout(20 * 1000);

            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {

                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            urlConnection.disconnect();
        }
        return result.toString();
    }

}
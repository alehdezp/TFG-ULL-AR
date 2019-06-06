package com.alehp.ull_navigation.Models;

import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class GetData extends AsyncTask<String, Void, String> {
    @Override
    protected String doInBackground(String... strings) {

        StringBuilder result= new StringBuilder();

        try {
            URL url = new URL(strings[0]);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(10000);
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            BufferedReader buffereReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while((line = buffereReader.readLine()) != null){
                result.append(line).append("\n");

            }

            return result.toString();


        }catch (IOException e){
            e.printStackTrace();
        }
            return "Error";
    }
};
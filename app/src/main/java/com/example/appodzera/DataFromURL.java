package com.example.appodzera;

import android.os.AsyncTask;
import java.io.InputStream;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import android.util.Log;


public class DataFromURL extends AsyncTask<String, Integer, String> {
    protected String doInBackground(String... urls){
        String data="";
        try{
            InputStream is = new URL(urls[0]).openStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String line="";
            while(line!=null){
                line = rd.readLine();
                data += line + "\n";
            }
        }
        catch(Exception e){}
        return data;
    }

    //protected void onProgressUpdate(Integer... progress) {
    protected void onProgressUpdate(Integer... progress) {

    }

    protected void onPostExecute(String result) {
        Log.d("info", result);
    }


}

//https://developer.android.com/reference/android/os/AsyncTask.html
//https://stackoverflow.com/questions/2778312/how-does-java-url-openstream-work
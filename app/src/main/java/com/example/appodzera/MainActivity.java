package com.example.appodzera;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.view.View;
import android.util.Log;
import android.widget.TextView;
import android.content.Intent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;

import database.async_tasks.DatabaseManipulation;
import database.entities.WeatherData;


public class MainActivity extends AppCompatActivity {
    MainActivity mainActivity;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainActivity=this;
    }

    public void UpdateText(String str){
        //TextView textView = findViewById(R.id.data);
        //if(textView==null) Log.d("error", "nie znaleziono");
        //textView.setText(str);
    }

    //TODO zamiast pixeli w xml zrobić wagi`

    public void ShowInfo(View view){
        startActivity(new Intent(this, ActivityWeatherInfo.class));
    }

    public void ShowData(View view){

    }

    public void DownloadData(View view){
        //new GetData().execute("http://mech.fis.agh.edu.pl/meteo/rest/json/last/s000");
    }

//    public class GetData extends AsyncTask<String, Integer, String> {
//
//        @Override
//        protected void onPreExecute(){
//            dialog = new ProgressDialog(mainActivity);
//            dialog.setMessage("Loading...");
//            dialog.show();
//        }
//
//        @Override
//        protected String doInBackground(String... urls){
//            Log.d("Info", "Retrieving data...");
//            String data="";
//            try{
//                InputStream is = new URL(urls[0]).openStream();
//                BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
//                String line="";
//                while(line!=null){
//                    line = rd.readLine();
//                    data += line + "\n";
//                }
//            }
//            catch(Exception e){}
//            return data;
//        }
//        @Override
//        protected void onProgressUpdate(Integer... progress) {}
//        @Override
//        protected void onPostExecute(String result) {
//            dialog.dismiss();
//            Log.d("info", result);
//            try {
//                JSONArray array = new JSONArray(result);
//                JSONObject whole_data = array.getJSONObject(0);
//                String station_id = whole_data.getString("station");
//                String time_update = whole_data.getString("utc");
//
//                JSONObject data = whole_data.getJSONObject("data");
//
//                Double temp = Double.parseDouble(data.getString("ta"));
//                Double pressure = Double.parseDouble(data.getString("p0"));
//                Double dew_temp = Double.parseDouble(data.getString("t0"));
//                Double hum = Double.parseDouble(data.getString("ha"));
//                Double rain_last_hour = Double.parseDouble(data.getString("r1"));
//                Double rain = Double.parseDouble(data.getString("ra"));
//                Double wind_dir = Double.parseDouble(data.getString("wd"));
//                Double wind_sp = Double.parseDouble(data.getString("wg"));
//                Double wind_sp_curr = Double.parseDouble(data.getString("ws"));
//                Double meters_ab_sea = Double.parseDouble(data.getString("h0"));
//                Log.d("temperature", temp.toString());
//                Log.d("time_update", time_update);
//
//                WeatherData weather_data = new WeatherData(time_update, station_id, pressure, temp, dew_temp,
//                            hum, rain_last_hour, rain, wind_dir, wind_sp, wind_sp_curr, meters_ab_sea);
//                new DatabaseManipulation(mainActivity, weather_data).execute();
//            }
//            catch (JSONException e){Log.d("error","Caught JSON exception");}
//        }
//    }
}


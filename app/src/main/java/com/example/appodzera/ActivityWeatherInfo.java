package com.example.appodzera;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;

import database.async_tasks.CreateDefaultStation;
import database.async_tasks.GetDefaultStation;
import database.async_tasks.GetLatestWeatherDataFromDatabase;
import database.async_tasks.GetWeatherDataFromURLRequest;
import database.async_tasks.InsertWeatherDataToDatabase;
import database.entities.WeatherData;

public class ActivityWeatherInfo extends AppCompatActivity {

    String default_station_id;
    WeatherData weather_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_info);
        default_station_id = GetDefaultStationId();
        weather_data = GetWeatherData();
        UpdateWeatherDataOnDisplay();
    }

    private String GetDefaultStationId(){
        String station_id = null;
        try {
            station_id = new GetDefaultStation(this).execute().get();
            if(station_id==null){
                station_id = new CreateDefaultStation(this).execute().get();
            }
            Log.d("info", "default station id: " + station_id);
        }
        catch(ExecutionException e){Log.d("info", "Execution exception");}
        catch(InterruptedException e){Log.d("info", "Interrupted exception");}
        return station_id;
    }

    private WeatherData GetWeatherData(){
        WeatherData weather_data = null;
        try {
            weather_data = new GetLatestWeatherDataFromDatabase(this, default_station_id).execute().get();
        }
        catch(ExecutionException e){Log.d("info", "Execution exception");}
        catch(InterruptedException e){Log.d("info", "Interrupted exception");}
        return weather_data;
    }

    public void DownloadWeatherData(View view){
        ProgressDialog progress_dialog = new ProgressDialog(this);
        progress_dialog.setMessage("Loading...");
        progress_dialog.show();
        WeatherData weather_data_to_insert = null;
        try {
            weather_data_to_insert = new GetWeatherDataFromURLRequest(this, default_station_id).execute().get();
        }
        catch(ExecutionException e){Log.d("info", "Execution exception");}
        catch(InterruptedException e){Log.d("info", "Interrupted exception");}
        if(weather_data_to_insert!=null){
            new InsertWeatherDataToDatabase(this, weather_data_to_insert);
        }
        weather_data = GetWeatherData();
        progress_dialog.dismiss();
        UpdateWeatherDataOnDisplay();
    }

    private void UpdateWeatherDataOnDisplay(){
        TextView textView = (TextView) findViewById(R.id.weather_data);
        if(weather_data!=null) textView.setText(weather_data.utc_time);
        else textView.setText("Brak danych");
    }
}

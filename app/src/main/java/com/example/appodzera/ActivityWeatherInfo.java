package com.example.appodzera;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;

import database.async_tasks.CreateDefaultStation;
import database.async_tasks.GetDefaultStation;
import database.async_tasks.GetLatestWeatherDataFromDatabase;
import database.async_tasks.GetWeatherDataFromURLRequest;
import database.async_tasks.InsertWeatherDataToDatabase;
import database.entities.Station;
import database.entities.WeatherData;

public class ActivityWeatherInfo extends AppCompatActivity {

    Station default_station;
    WeatherData weather_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_info);
        default_station = GetDefaultStation();
        weather_data = GetWeatherData();
        UpdateWeatherDataOnDisplay();
        UpdateDefaultStationOnDisplay();
    }

    public void ShowStations(View view){
        startActivity(new Intent(this, ActivityStationsInfo.class));
    }


    private Station GetDefaultStation(){
        Station station = null;
        try {
            station = new GetDefaultStation(this).execute().get();
            if(station==null){
                station = new CreateDefaultStation(this).execute().get();
            }
            Log.d("info", "default station id: " + station.id);
        }
        catch(ExecutionException e){Log.d("info", "Execution exception");}
        catch(InterruptedException e){Log.d("info", "Interrupted exception");}
        return station;
    }

    private WeatherData GetWeatherData(){
        WeatherData weather_data = null;
        try {
            weather_data = new GetLatestWeatherDataFromDatabase(this, default_station.id).execute().get();
        }
        catch(ExecutionException e){Log.d("info", "Execution exception");}
        catch(InterruptedException e){Log.d("info", "Interrupted exception");}
        return weather_data;
    }

    public void DownloadWeatherData(View view){
        ProgressDialog progress_dialog = new ProgressDialog(this);
        progress_dialog.setMessage("Loading...");
        progress_dialog.setCancelable(false);
        progress_dialog.show();
        //TODO progress dialog doesnt work!!
        WeatherData weather_data_to_insert = null;
        try {
            weather_data_to_insert = new GetWeatherDataFromURLRequest(this, default_station.id).execute().get();
        }
        catch(ExecutionException e){Log.d("info", "Execution exception");}
        catch(InterruptedException e){Log.d("info", "Interrupted exception");}
        if(weather_data_to_insert!=null){
            new InsertWeatherDataToDatabase(this, weather_data_to_insert).execute();
        }
        weather_data = GetWeatherData();
        progress_dialog.dismiss();
        UpdateWeatherDataOnDisplay();
    }

    private void UpdateWeatherDataOnDisplay(){
        TextView textView_utc_time = (TextView) findViewById(R.id.weather_data_utc_time);
        TextView textView_temp = (TextView) findViewById(R.id.weather_data_temp);
        TextView textView_dew_temp = (TextView) findViewById(R.id.weather_data_dew_temp);
        TextView textView_wind = (TextView) findViewById(R.id.weather_data_wind);
        if(weather_data!=null){
            textView_utc_time.setText(weather_data.utc_time);
            textView_temp.setText(Double.toString(weather_data.temperature)+" "+(char)0x00B0+"C");
            textView_dew_temp.setText(Double.toString(weather_data.dew_point_temperature)+" "+(char)0x00B0+"C");
            textView_wind.setText(Double.toString(weather_data.wind_speed)+" m/s");
        }
        else textView_utc_time.setText("Brak danych");
    }

    private void UpdateDefaultStationOnDisplay(){
        Button button = (Button) findViewById(R.id.button_stations);
        if(weather_data!=null) button.setText("Aktualnie wybrana stacja:\n" + default_station.name);
        else button.setText("Brak domyślnej stacji");
    }

}

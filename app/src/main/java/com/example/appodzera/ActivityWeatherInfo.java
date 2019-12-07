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

    @Override
    protected void onResume(){
        super.onResume();
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
        TextView textView_wind_dir = (TextView) findViewById(R.id.weather_data_wind_dir);
        TextView textView_pressure = (TextView) findViewById(R.id.weather_data_pressure);
        TextView textView_humidity = (TextView) findViewById(R.id.weather_data_humidity);
        TextView textView_rainfall = (TextView) findViewById(R.id.weather_data_rainfall);

        // TODO przeniesc to do innej klasy...

        if(weather_data!=null){
            textView_utc_time.setText(weather_data.utc_time);
            if(weather_data.temperature==-999.0) textView_temp.setText("brak");
            else textView_temp.setText(Double.toString(weather_data.temperature)+" "+(char)0x00B0+"C");
            if(weather_data.dew_point_temperature==-999.0) textView_dew_temp.setText("brak");
            else textView_dew_temp.setText(Double.toString(weather_data.dew_point_temperature)+" "+(char)0x00B0+"C");
            if(weather_data.wind_speed==-999.0) textView_wind.setText("brak");
            else textView_wind.setText(Double.toString(weather_data.wind_speed)+" m/s");
            if(weather_data.wind_direction==-999.0) textView_wind_dir.setText("brak");
            else{
                if(weather_data.wind_direction>337.5 || weather_data.wind_direction<22.5)
                    textView_wind_dir.setText("Północ");
                if(weather_data.wind_direction>22.5 || weather_data.wind_direction<67.5)
                    textView_wind_dir.setText("Pn.-Wsch.");
                if(weather_data.wind_direction>67.5 || weather_data.wind_direction<112.5)
                    textView_wind_dir.setText("Wschód");
                if(weather_data.wind_direction>112.5 || weather_data.wind_direction<157.5)
                    textView_wind_dir.setText("Pd.-Wsch.");
                if(weather_data.wind_direction>157.5 || weather_data.wind_direction<202.5)
                    textView_wind_dir.setText("Południe");
                if(weather_data.wind_direction>202.5 || weather_data.wind_direction<247.5)
                    textView_wind_dir.setText("Pd.-Zach.");
                if(weather_data.wind_direction>247.5 || weather_data.wind_direction<292.5)
                    textView_wind_dir.setText("Zachód");
                if(weather_data.wind_direction>292.5 || weather_data.wind_direction<337.5)
                    textView_wind_dir.setText("Pn.-Zach.");
            }
            if(weather_data.pressure==-999.0) textView_pressure.setText("brak");
            else textView_pressure.setText(Double.toString(weather_data.pressure)+" Pa");
            if(weather_data.humidity==-999.0) textView_humidity.setText("brak");
            else textView_humidity.setText(Double.toString(weather_data.humidity)+" %");
            if(weather_data.rainfall_intensity==-999.0) textView_rainfall.setText("brak");
            else textView_rainfall.setText(Double.toString(weather_data.rainfall_intensity)+" mm");
        }
        else textView_utc_time.setText("Brak danych");
    }

    private void UpdateDefaultStationOnDisplay(){
        Button button = (Button) findViewById(R.id.button_stations);
        if(weather_data!=null) button.setText("Aktualnie wybrana stacja:\n" + default_station.name);
        else button.setText("Brak domyślnej stacji");
    }

}

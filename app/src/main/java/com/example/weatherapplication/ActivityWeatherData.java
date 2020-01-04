package com.example.weatherapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;

import database.async_tasks.CreateDefaultStation;
import database.async_tasks.CreateDefaultWeatherDataDisplayPreferences;
import database.async_tasks.GetDefaultStation;
import database.async_tasks.GetLatestWeatherDataFromDatabase;
import database.async_tasks.GetWeatherDataDisplayPreferences;
import database.async_tasks.GetWeatherDataFromURLRequest;
import database.async_tasks.InsertWeatherDataToDatabase;
import database.entities.Station;
import database.entities.WeatherData;
import database.entities.WeatherDataDisplayPreferences;

public class ActivityWeatherData extends AppCompatActivity {

    ActivityWeatherData activity;
    Station default_station;
    WeatherData weather_data;
    ProgressDialog progress_dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_data);
        default_station = GetDefaultStation();
        weather_data = GetWeatherData();
        UpdateWeatherDataOnDisplay();
        UpdateDefaultStationOnDisplay();

        progress_dialog= new ProgressDialog(this);
        progress_dialog.setMessage("Pobieram dane. To może chwilę potrwać...");
        progress_dialog.setCancelable(false);

        activity = this;
    }

    @Override
    protected void onResume(){
        super.onResume();
        default_station = GetDefaultStation();
        weather_data = GetWeatherData();
        UpdateWeatherDataOnDisplay();
        UpdateDefaultStationOnDisplay();
    }

    @Override
    protected void onPause(){
        super.onPause();
        DismissProgressDialog();
    }

    public void ShowStations(View view){
        startActivity(new Intent(this, ActivityStationsInfo.class));
    }

    private void DismissProgressDialog(){
        progress_dialog.dismiss();
    }

    private Station GetDefaultStation(){
        Station station = null;
        try {
            station = new GetDefaultStation(this).execute().get();
            if(station==null) {
                new CreateDefaultStation(this).execute();
            }
        }
        catch(ExecutionException e){Log.d("info", "Execution exception");}
        catch(InterruptedException e){Log.d("info", "Interrupted exception");}
        return station;
    }

    private WeatherData GetWeatherData(){
        WeatherData weather_data = null;
        if(default_station!=null){
            try {
                weather_data = new GetLatestWeatherDataFromDatabase(this, default_station.id).execute().get();
            }
            catch(ExecutionException e){Log.d("info", "Execution exception");}
            catch(InterruptedException e){Log.d("info", "Interrupted exception");}
        }
        return weather_data;
    }

    public void DownloadWeatherData(View view){
        if(default_station!=null) {
            progress_dialog.show();

            new Thread(new Runnable() {
                @Override
                public void run()
                {
                    WeatherData weather_data_to_insert = null;
                    try {
                        weather_data_to_insert = new GetWeatherDataFromURLRequest(default_station.id).execute().get();
                    } catch (ExecutionException e) {
                        Log.d("info", "Execution exception");
                    } catch (InterruptedException e) {
                        Log.d("info", "Interrupted exception");
                    }
                    if (weather_data_to_insert != null) {
                        new InsertWeatherDataToDatabase(activity, weather_data_to_insert).execute();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run()
                        {
                            weather_data = GetWeatherData();
                            UpdateWeatherDataOnDisplay();
                            DismissProgressDialog();
                        }
                    });
                }
            }).start();
        }
        else{
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Nie wybrano domyślnej stacji.").setTitle("WYBIERZ STACJĘ");
            builder.show();
        }
    }

    private void UpdateWeatherDataOnDisplay(){
        WeatherDataDisplayPreferences preferences = null;
        try {
            preferences = new GetWeatherDataDisplayPreferences(activity).execute().get();
        } catch (ExecutionException e) {
            Log.d("info", "Execution exception");
        } catch (InterruptedException e) {
            Log.d("info", "Interrupted exception");
        }
        if(preferences==null){
            try{
                Log.d("info", "preferencje nie zdefiniowane");
                preferences = new CreateDefaultWeatherDataDisplayPreferences(activity).execute().get();
            } catch (ExecutionException e) {
                Log.d("info", "Execution exception");
            } catch (InterruptedException e) {
                Log.d("info", "Interrupted exception");
            }
        }

        if(preferences.local_time==false) findViewById(R.id.row_time_local).setVisibility(View.GONE);
        if(preferences.utc_time==false) findViewById(R.id.row_time_utc).setVisibility(View.GONE);
        if(preferences.temperature==false) findViewById(R.id.row_temp).setVisibility(View.GONE);
        if(preferences.dew_point_temperature==false) findViewById(R.id.row_dew_temp).setVisibility(View.GONE);
        if(preferences.wind_speed==false) findViewById(R.id.row_wind).setVisibility(View.GONE);
        if(preferences.wind_speed_current==false) findViewById(R.id.row_wind_current).setVisibility(View.GONE);
        if(preferences.wind_direction==false) findViewById(R.id.row_wind_dir).setVisibility(View.GONE);
        if(preferences.rainfall_intensity==false) findViewById(R.id.row_rainfall).setVisibility(View.GONE);
        if(preferences.rainfall_intensity_in_last_hour==false) findViewById(R.id.row_rainfall_in_last_hour).setVisibility(View.GONE);
        if(preferences.pressure==false) findViewById(R.id.row_pressure).setVisibility(View.GONE);
        if(preferences.humidity==false) findViewById(R.id.row_humidity).setVisibility(View.GONE);

        TextView textView_local_time = findViewById(R.id.weather_data_time_local);
        TextView textView_utc_time = findViewById(R.id.weather_data_time_utc);
        TextView textView_temp = findViewById(R.id.weather_data_temp);
        TextView textView_dew_temp = findViewById(R.id.weather_data_dew_temp);
        TextView textView_wind = findViewById(R.id.weather_data_wind);
        TextView textView_wind_current = findViewById(R.id.weather_data_wind_current);
        TextView textView_wind_dir = findViewById(R.id.weather_data_wind_dir);
        TextView textView_pressure = findViewById(R.id.weather_data_pressure);
        TextView textView_humidity = findViewById(R.id.weather_data_humidity);
        TextView textView_rainfall = findViewById(R.id.weather_data_rainfall);
        TextView textView_rainfall_in_last_hour = findViewById(R.id.weather_data_rainfall_in_last_hour);

        // TODO przeniesc to do innej klasy...

        if(weather_data!=null){
            textView_local_time.setText(weather_data.local_time);
            textView_utc_time.setText(weather_data.utc_time);
            if(weather_data.temperature==-999.0) textView_temp.setText("brak");
            else textView_temp.setText(Double.toString(weather_data.temperature)+" "+(char)0x00B0+"C");
            if(weather_data.dew_point_temperature==-999.0) textView_dew_temp.setText("brak");
            else textView_dew_temp.setText(Double.toString(weather_data.dew_point_temperature)+" "+(char)0x00B0+"C");
            if(weather_data.wind_speed==-999.0) textView_wind.setText("brak");
            else textView_wind.setText(Double.toString(weather_data.wind_speed)+" m/s");
            if(weather_data.wind_speed_current==-999.0) textView_wind_current.setText("brak");
            else textView_wind_current.setText(Double.toString(weather_data.wind_speed_current)+" m/s");
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
            else textView_pressure.setText(Double.toString(weather_data.pressure)+" hPa");
            if(weather_data.humidity==-999.0) textView_humidity.setText("brak");
            else textView_humidity.setText(Double.toString(weather_data.humidity)+" %");
            if(weather_data.rainfall_intensity==-999.0) textView_rainfall.setText("brak");
            else textView_rainfall.setText(Double.toString(weather_data.rainfall_intensity)+" mm");
            if(weather_data.rainfall_intensity_in_last_hour==-999.0) textView_rainfall_in_last_hour.setText("brak");
            else textView_rainfall_in_last_hour.setText(Double.toString(weather_data.rainfall_intensity)+" mm");
        }
        else{
            textView_local_time.setText("Brak danych");
            textView_temp.setText("Brak danych");
            textView_dew_temp.setText("Brak danych");
            textView_wind.setText("Brak danych");
            textView_wind_dir.setText("Brak danych");
            textView_pressure.setText("Brak danych");
            textView_humidity.setText("Brak danych");
            textView_rainfall.setText("Brak danych");
        }
    }

    private void UpdateDefaultStationOnDisplay(){
        Button button = findViewById(R.id.button_stations);
        if(default_station!=null) button.setText("Aktualnie wybrana stacja:\n" + default_station.name);
        else button.setText("Brak domyślnej stacji");
    }

}

package com.example.weatherapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import database.async_tasks.DeleteAllStationsAndWeatherData;
import database.async_tasks.DeleteWeatherDataDisplayPreferences;
import database.async_tasks.InsertWeatherDataDisplayPreferences;
import database.entities.WeatherDataDisplayPreferences;

public class ActivitySettings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    public void DeleteDatabaseContent(View view){
        new DeleteAllStationsAndWeatherData(this).execute();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Usunięto dane.");
        builder.show();
    }

    public void SetWeatherDataDisplayPreferences(View view){
        new DeleteWeatherDataDisplayPreferences(this).execute();

        CheckBox checkbox_local_time = (CheckBox)findViewById(R.id.checkbox_local_time);
        CheckBox checkbox_utc_time = (CheckBox)findViewById(R.id.checkbox_utc_time);
        CheckBox checkbox_pressure = (CheckBox)findViewById(R.id.checkbox_pressure);
        CheckBox checkbox_temperature = (CheckBox)findViewById(R.id.checkbox_temperature);
        CheckBox checkbox_dew_point_temperature = (CheckBox)findViewById(R.id.checkbox_temperature_dew_point);
        CheckBox checkbox_humidity = (CheckBox)findViewById(R.id.checkbox_humidity);
        CheckBox checkbox_rainfall_intensity_in_last_hour = (CheckBox)findViewById(R.id.checkbox_rainfall_intensity_in_last_hour);
        CheckBox checkbox_rainfall_intensity = (CheckBox)findViewById(R.id.checkbox_rainfall_intensity);
        CheckBox checkbox_wind_direction = (CheckBox)findViewById(R.id.checkbox_wind_direction);
        CheckBox checkbox_wind_speed = (CheckBox)findViewById(R.id.checkbox_wind_speed);
        CheckBox checkbox_wind_speed_current = (CheckBox)findViewById(R.id.checkbox_wind_speed_current);

        int id = 0;
        boolean local_time = false;
        boolean utc_time = false;
        boolean pressure = false;
        boolean temperature = false;
        boolean dew_point_temperature = false;
        boolean humidity = false;
        boolean rainfall_intensity_in_last_hour = false;
        boolean rainfall_intensity = false;
        boolean wind_direction = false;
        boolean wind_speed = false;
        boolean wind_speed_current = false;

        if(checkbox_local_time.isChecked()) local_time = true;
        if(checkbox_utc_time.isChecked()) utc_time = true;
        if(checkbox_pressure.isChecked()) pressure = true;
        if(checkbox_temperature.isChecked()) temperature = true;
        if(checkbox_dew_point_temperature.isChecked()) dew_point_temperature = true;
        if(checkbox_humidity.isChecked()) humidity = true;
        if(checkbox_rainfall_intensity_in_last_hour.isChecked()) rainfall_intensity_in_last_hour = true;
        if(checkbox_rainfall_intensity.isChecked()) rainfall_intensity = true;
        if(checkbox_wind_direction.isChecked()) wind_direction = true;
        if(checkbox_wind_speed.isChecked()) wind_speed = true;
        if(checkbox_wind_speed_current.isChecked()) wind_speed_current = true;

        WeatherDataDisplayPreferences preferences = new WeatherDataDisplayPreferences(id, utc_time, local_time, pressure, temperature,
                dew_point_temperature, humidity, rainfall_intensity_in_last_hour, rainfall_intensity, wind_direction, wind_speed, wind_speed_current);

        new InsertWeatherDataDisplayPreferences(this, preferences).execute();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Zapamiętano.");
        builder.show();
    }
}

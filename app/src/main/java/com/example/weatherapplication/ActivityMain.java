package com.example.weatherapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.content.Intent;

import com.example.weatherapplicationandroid.BarChartActivity;


public class ActivityMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //TODO pozmieniac pixele na dpi, a dane pogodowe przerobic na liste

    public void ShowWeatherData(View view){startActivity(new Intent(this, ActivityWeatherData.class));}
    public void ShowWeatherChart(View view){startActivity(new Intent(this, ActivityWeatherChart.class));}
    public void ShowSettings(View view){startActivity(new Intent(this, ActivitySettings.class));}
    public void ShowApplicationInfo(View view){startActivity(new Intent(this, ActivityApplicationInfo.class));}
}


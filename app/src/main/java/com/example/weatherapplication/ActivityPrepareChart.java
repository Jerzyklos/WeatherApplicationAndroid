package com.example.weatherapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.weatherapplication.R;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

public class ActivityPrepareChart extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prepare_chart);
    }

    public void TodayDateSelected(View view){
        findViewById(R.id.textview_char_preparation_date_info).setVisibility(View.GONE);
        findViewById(R.id.input_date).setVisibility(View.GONE);
    }

    public void OtherDateSelected(View view){
        findViewById(R.id.textview_char_preparation_date_info).setVisibility(View.VISIBLE);
        findViewById(R.id.input_date).setVisibility(View.VISIBLE);
    }

    public void DrawChart(View view){
        Intent intent = new Intent(this, ActivityWeatherChart.class);
        Bundle bundle = new Bundle();
        double array[] = {1.0, 2.0};
        bundle.putDoubleArray("data", array);
        intent.putExtras(bundle);
        ActivityPrepareChart.this.startActivity(intent);
    }
}

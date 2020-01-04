package com.example.weatherapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.weatherapplication.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class WeatherChartActivity extends AppCompatActivity {

    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        double array[];
        Bundle bundle = getIntent().getExtras();
        array = bundle.getDoubleArray("data");

        String param = bundle.getString("param");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        LineChart chart = findViewById(R.id.linechart);

        ArrayList<Entry> values = new ArrayList();

        for(int i=0; i<array.length; i++){
            values.add(new Entry(i, (float) array[i]));
        }

        LineDataSet dataSet = new LineDataSet(values, param);

        LineData data = new LineData(dataSet);
        chart.setData(data);
        chart.animateX(2500);
        //refresh
        chart.invalidate();
    }
}

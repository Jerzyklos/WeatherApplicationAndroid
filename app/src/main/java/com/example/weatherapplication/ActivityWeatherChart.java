package com.example.weatherapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.weatherapplication.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class ActivityWeatherChart extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        BarChart chart = findViewById(R.id.barchart);

        Float[] tab = {Float.valueOf("2008"), Float.valueOf("2009"), Float.valueOf("2010"), Float.valueOf("2011"), Float.valueOf("2012")};

//        List<BarEntry> entries = new ArrayList<>();
//        entries.add(new BarEntry(0f, otf.floatValue()));
//        entries.add(new BarEntry(1f, ptf.floatValue()));
//        entries.add(new BarEntry(2f, ltf.floatValue()));
//        entries.add(new BarEntry(3f, pertf.floatValue()));
        ArrayList NoOfEmp = new ArrayList();

        NoOfEmp.add(new BarEntry(tab[0].floatValue(), 0, "2008"));
        NoOfEmp.add(new BarEntry(tab[1].floatValue(), 1, "2008"));
        NoOfEmp.add(new BarEntry(tab[2].floatValue(), 2, "2008"));
        NoOfEmp.add(new BarEntry(tab[3].floatValue(), 3, "2008"));
        NoOfEmp.add(new BarEntry(tab[4].floatValue(), 4, "2008"));

        ArrayList year = new ArrayList();

        year.add("2008");
        year.add("2008");
        year.add("2008");
        year.add("2008");
        year.add("2008");

        BarDataSet bardataset1 = new BarDataSet(NoOfEmp, "No Of Employee");
        //BarDataSet bardataset2 = new BarDataSet(year, "Year");
        chart.animateY(2000);
        BarData data = new BarData(bardataset1);

        bardataset1.setColors(ColorTemplate.COLORFUL_COLORS);
        chart.setData(data);
    }
}

package com.example.weatherapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import database.async_tasks.GetDefaultStation;
import database.async_tasks.GetWeatherDataForChartFromURLRequest;
import database.entities.Station;

public class ActivityPrepareChart extends AppCompatActivity {

    private ActivityPrepareChart activity;
    private List<Double> data;
    private Bundle bundle;
    private String date;
    private String param;
    private String station_id;
    private ProgressDialog progress_dialog;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prepare_chart);

        date=TodayToString();
        param = null;
        station_id="s000";
        data = null;
        activity = this;
        bundle = new Bundle();
        intent = new Intent(this, WeatherChartActivity.class);

        progress_dialog= new ProgressDialog(this);
        progress_dialog.setMessage("Pobieram dane. To może chwilę potrwać...");
        progress_dialog.setCancelable(false);
    }

    public void TodayDateSelected(View view){
        date=TodayToString();
    }

    public String TodayToString(){
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        Log.d("today date", date);
        return date;
    }

    public void DismissProgressDialog(){
        progress_dialog.dismiss();
    }

    public void OtherDateSelected(View view){
        final Calendar myCalendar = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            }
        };

        new DatePickerDialog(ActivityPrepareChart.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();

        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);

        Date calendar_date = myCalendar.getTime();
        this.date = sdf.format(calendar_date);
    }

    public void DrawChart(View view){
        RadioButton temp = findViewById(R.id.radiobutton_temperature);
        RadioButton temp_dew_point = findViewById(R.id.radiobutton_temperature_dew_point);
        RadioButton pressure = findViewById(R.id.radiobutton_pressure);
        RadioButton humidity = findViewById(R.id.radiobutton_humidity);
        RadioButton rainfall = findViewById(R.id.radiobutton_rainfall_intensity);
        RadioButton rainfall_in_last_hour = findViewById(R.id.radiobutton_rainfall_intensity_in_last_hour);
        RadioButton wind_dir = findViewById(R.id.radiobutton_wind_direction);
        RadioButton wind_speed = findViewById(R.id.radiobutton_wind_speed);
        RadioButton wind_speed_current = findViewById(R.id.radiobutton_wind_speed_current);

        param = null;
        if(temp.isChecked()) param = "temp";
        if(temp_dew_point.isChecked()) param = "temp0";
        if(pressure.isChecked()) param = "pres0";
        if(humidity.isChecked()) param = "humi";
        if(rainfall_in_last_hour.isChecked()) param = "rain1";
        if(rainfall.isChecked()) param = "rain";
        if(wind_dir.isChecked()) param = "windd";
        if(wind_speed.isChecked()) param = "winds";
        if(wind_speed_current.isChecked()) param = "windg";

        if(param!=null) {
            Station station = null;
            try {
                station = new GetDefaultStation(this).execute().get();
            } catch (ExecutionException e) {
                Log.d("info", "Execution exception");
            } catch (InterruptedException e) {
                Log.d("info", "Interrupted exception");
            }
            if(station==null){
                station_id = "s000";
                Toast.makeText(this.getApplicationContext(), "Brak stacji domyślnej, pobieram dane dla WFiIS AGH.", Toast.LENGTH_LONG).show();
            }
            else station_id = station.id;


            progress_dialog.show();

            new Thread(new Runnable() {
                @Override
                public void run()
                {
                    try {
                        data =  new GetWeatherDataForChartFromURLRequest(activity, station_id, param, date).execute().get();
                    } catch (ExecutionException e) {
                        Log.d("info", "Execution exception");
                    } catch (InterruptedException e) {
                        Log.d("info", "Interrupted exception");
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run()
                        {
                            double array[]=new double[data.size()];
                            for(int i=0; i<data.size(); i++){
                                array[i] = data.get(i);
                            }
                            bundle.putDoubleArray("data", array);
                            bundle.putString("parameter", param);
                            DismissProgressDialog();
                            intent.putExtras(bundle);
                            ActivityPrepareChart.this.startActivity(intent);
                        }
                    });
                }
            }).start();

        }
        else Toast.makeText(this.getApplicationContext(), "Nie wybrano parametru do wyświetlenia.", Toast.LENGTH_SHORT).show();
    }
}

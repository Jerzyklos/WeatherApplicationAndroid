package com.example.appodzera;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.List;
import java.util.concurrent.ExecutionException;

import database.async_tasks.GetAllStations;
import database.async_tasks.GetStationsFromURLRequest;
import database.entities.Station;

public class ActivityStationsInfo extends AppCompatActivity {

    List<Station> stations_to_show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stations_info);

        stations_to_show = GetStationsFromDatabase();
        if(stations_to_show==null){
            //TODO create
        }
        else{
            for(Station st : stations_to_show){Log.d("info", st.name);}
        }
    }

    public void DownloadStations(View view){
        //TODO tu pobieranie
        List<Station> stations = null;
        try {
            stations = new GetStationsFromURLRequest(this).execute().get();
        }
        catch(ExecutionException e){Log.d("info", "Execution exception");}
        catch(InterruptedException e){Log.d("info", "Interrupted exception");}
        for(Station st : stations) Log.d("info", st.name);
        //UpdateStationsOnDisplay(stations);
    }

    void UpdateStationsOnDisplay(List<Station> stations){

    }

    public List<Station> GetStationsFromDatabase() {
        List<Station> stations = null;
        try {
            stations = new GetAllStations(this).execute().get();
        }
        catch (ExecutionException e){Log.d("info", "Execution exception");}
        catch (InterruptedException e){Log.d("info", "Interrupted exception");}
        return stations;
    }
}

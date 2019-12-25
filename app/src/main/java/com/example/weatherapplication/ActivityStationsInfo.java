package com.example.weatherapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import database.async_tasks.GetAllStations;
import database.async_tasks.GetStationsFromURLRequest;
import database.async_tasks.InsertStationsToDatabase;
import database.async_tasks.InsertWeatherDataToDatabase;
import database.async_tasks.SetStationAsDefault;
import database.entities.Station;

public class ActivityStationsInfo extends AppCompatActivity {

    List<Station> stations;
    Station default_station;
    ListView listview_of_stations;
    ActivityStationsInfo activity;
    ProgressDialog progress_dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stations_info);

        progress_dialog= new ProgressDialog(this);
        progress_dialog.setMessage("Pobieram stacje...");
        progress_dialog.setCancelable(false);

        activity = this;
        listview_of_stations = (ListView) findViewById(R.id.listview_of_stations);
        stations = GetStationsFromDatabase();
        default_station = null;
        if(stations==null){
            //TODO coś zrobić jak jest nullem
        }
        else {
            for (Station station : stations) {
                if (station.is_default_station == true) default_station = station;
            }
            UpdateStationsOnDisplay();
        }

    }

    @Override
    protected void onPause(){
        super.onPause();
        DismissProgressDialog();
    }

    private void DismissProgressDialog(){
        progress_dialog.dismiss();
    }

    public void DownloadStations(View view){
        progress_dialog.show();

        new Thread(new Runnable() {
            @Override
            public void run()
            {
                List<Station> downloaded_stations = null;
                try {
                    downloaded_stations = new GetStationsFromURLRequest(activity).execute().get();
                } catch (ExecutionException e) {
                    Log.d("info", "Execution exception");
                } catch (InterruptedException e) {
                    Log.d("info", "Interrupted exception");
                }
                if(downloaded_stations!=null) {
                    new InsertStationsToDatabase(activity, downloaded_stations).execute();
                    stations = downloaded_stations;
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run()
                    {
                        UpdateStationsOnDisplay();
                        DismissProgressDialog();
                    }
                });
            }
        }).start();
    }

    void UpdateStationsOnDisplay(){
        final List<String> stations_names = new ArrayList<>();
        for(Station st : stations){stations_names.add(st.name);}

        final ListViewAdapter adapter = new ListViewAdapter(this, R.layout.activity_text_view_for_list_view, stations_names);
        listview_of_stations.setAdapter(adapter);

        listview_of_stations.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id){
                final String clicked_station_name = adapter.getItem(position);

                Station new_default_station = null;
                for(Station st : stations){
                    if(st.name==clicked_station_name){new_default_station = st;}
                }
                if(default_station==null){
                    Log.d("info", "wchodzi 1");
                    new_default_station.is_default_station = true;
                    new SetStationAsDefault(activity, new_default_station, null).execute();
                }
                else {
                    Log.d("info", "wchodzi 2");
                    new_default_station.is_default_station = true;
                    default_station.is_default_station = false;
                    new SetStationAsDefault(activity, new_default_station, default_station).execute();
                }
                activity.finish();
            }

        });

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

    private class ListViewAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public ListViewAdapter(Context context, int textViewResourceId,
                                  List<String> names) {
            super(context, textViewResourceId, names);
            for (int i = 0; i < names.size(); ++i) {
                mIdMap.put(names.get(i), i);
            }
        }

        @Override
        public long getItemId(int position) {
            String item = getItem(position);
            return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

    }

}

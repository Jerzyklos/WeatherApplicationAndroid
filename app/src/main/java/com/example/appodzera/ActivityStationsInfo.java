package com.example.appodzera;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import database.async_tasks.GetAllStations;
import database.async_tasks.GetStationsFromURLRequest;
import database.async_tasks.InsertStationsToDatabase;
import database.entities.Station;

public class ActivityStationsInfo extends AppCompatActivity {

    List<Station> stations;
    ListView listview_of_stations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stations_info);

        stations = GetStationsFromDatabase();
        if(stations==null){
            //TODO create
        }
        else{
            List<String> stations_names = new ArrayList<>();
            for(Station st : stations){stations_names.add(st.name);}

            listview_of_stations = (ListView) findViewById(R.id.listview_of_stations);
            ListViewAdapter adapter = new ListViewAdapter(this, R.layout.activity_text_view_for_list_view, stations_names);
            listview_of_stations.setAdapter(adapter);
        }
    }

    // TODO 1. przerzucic wszystki z listview do update'a
    // TODO 2. zrobić onClicka do listy
    // TODO 3. Zrobić podświetlenie defaultowej stacji(mozna ja przeslac w konstruktorze)

    public void DownloadStations(View view){
        //TODO tu pobieranie
        List<Station> downloaded_stations = null;
        try {
            downloaded_stations = new GetStationsFromURLRequest(this).execute().get();
        }
        catch(ExecutionException e){Log.d("info", "Execution exception");}
        catch(InterruptedException e){Log.d("info", "Interrupted exception");}

        new InsertStationsToDatabase(this, downloaded_stations).execute();
        stations = downloaded_stations;

        UpdateStationsOnDisplay();
    }

    void UpdateStationsOnDisplay(){
        List<String> stations_names = new ArrayList<>();
        for(Station st : stations){stations_names.add(st.name);}

        listview_of_stations = (ListView) findViewById(R.id.listview_of_stations);
        ListViewAdapter adapter = new ListViewAdapter(this, R.layout.activity_text_view_for_list_view, stations_names);
        listview_of_stations.setAdapter(adapter);
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
                                  List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
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

/*


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listviewexampleactivity);

        final ListView listview = (ListView) findViewById(R.id.listview);
        String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
                "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
                "Linux", "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux",
                "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux", "OS/2",
                "Android", "iPhone", "WindowsMobile" };

        final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < values.length; ++i) {
            list.add(values[i]);
        }
        final StableArrayAdapter adapter = new StableArrayAdapter(this,
                android.R.layout.simple_list_item_1, list);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                    int position, long id) {
                final String item = (String) parent.getItemAtPosition(position);
                view.animate().setDuration(2000).alpha(0)
                        .withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                list.remove(item);
                                adapter.notifyDataSetChanged();
                                view.setAlpha(1);
                            }
                        });
            }

        });
    }

    private class StableArrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StableArrayAdapter(Context context, int textViewResourceId,
                List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
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


 */
package database.async_tasks;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.List;

import database.AppDatabase;
import database.daos.StationDao;
import database.entities.Station;


public class InsertStationsToDatabase extends AsyncTask<Void, Void, Integer> {

    private WeakReference<Activity> activity;
    private List<Station> stations;

    public InsertStationsToDatabase(Activity activity, List<Station> stations){
        this.activity = new WeakReference<>(activity);
        this.stations = stations;
    }

    @Override
    protected Integer doInBackground(Void... params) {
        StationDao station_Dao = AppDatabase.getInstance(this.activity.get()).getStationDao();
        Log.d("info", "Inserting stations to database...");
        for(Station station : stations){station_Dao.insert(station);}
        return 1;
    }

    @Override
    protected void onPostExecute(Integer result){Log.d("info", "Stations inserted.");
    }
}

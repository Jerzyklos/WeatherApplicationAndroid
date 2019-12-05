package database.async_tasks;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import java.lang.ref.WeakReference;

import database.AppDatabase;
import database.daos.StationDao;
import database.entities.Station;

public class CreateDefaultStation extends AsyncTask<Void, Void, Station> {

    private WeakReference<Activity> activity;

    public CreateDefaultStation(Activity activity){
        this.activity = new WeakReference<>(activity);
    }

    @Override
    protected Station doInBackground(Void... params) {
        StationDao station_Dao = AppDatabase.getInstance(this.activity.get()).getStationDao();
        Log.d("info", "creating default station...");
        Station station = new Station("s000", "WFiIS AGH", 19.9129, 50.0670, 220, true);
        station_Dao.insert(station);
        return station;
    }

    @Override
    protected void onPostExecute(Station station){
        Log.d("info", "Created default station");
    }
}

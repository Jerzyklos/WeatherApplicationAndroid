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
        Station station_wfiis = new Station("s000", "WFiIS AGH", 19.9129, 50.0670, 220, false);
        Station station_balice = new Station("s502", "Balice", 19.7946, 50.089, 240, false);
        station_Dao.insert(station_wfiis);
        station_Dao.insert(station_balice);
        return station_wfiis;
    }

    @Override
    protected void onPostExecute(Station station){
        Log.d("info", "Created default station");
    }
}

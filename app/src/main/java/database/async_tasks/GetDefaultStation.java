package database.async_tasks;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import java.lang.ref.WeakReference;

import database.AppDatabase;
import database.daos.StationDao;
import database.daos.WeatherDataDao;
import database.entities.Station;

public class GetDefaultStation extends AsyncTask<Void, Void, Station> {

    private WeakReference<Activity> activity;

    public GetDefaultStation(Activity activity){
        this.activity = new WeakReference<>(activity);
    }

    @Override
    protected Station doInBackground(Void... params) {
        Log.d("info", "Getting default station..");
        StationDao station_Dao = AppDatabase.getInstance(this.activity.get()).getStationDao();
        Station station =  station_Dao.getDefaultStation();
        if(station==null) return null;
        else return station;
    }

    @Override
    protected void onPostExecute(Station station) {
        Log.d("info", "Done");
    }
}
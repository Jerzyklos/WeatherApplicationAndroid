package database.async_tasks;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import java.lang.ref.WeakReference;

import database.AppDatabase;
import database.daos.StationDao;
import database.daos.WeatherDataDao;
import database.entities.Station;

public class GetDefaultStation extends AsyncTask<Void, Void, String> {

    private WeakReference<Activity> activity;

    public GetDefaultStation(Activity activity){
        this.activity = new WeakReference<>(activity);
    }

    @Override
    protected String doInBackground(Void... params) {
        Log.d("info", "Getting default station..");
        StationDao station_Dao = AppDatabase.getInstance(this.activity.get()).getStationDao();
        Station station =  station_Dao.getDefaultStation();
        if(station==null) return null;
        else return station.id;
    }

    @Override
    protected void onPostExecute(String station_id) {
        Log.d("info", "Done");
    }
}
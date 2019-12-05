package database.async_tasks;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.List;

import database.AppDatabase;
import database.daos.StationDao;
import database.entities.Station;

public class GetAllStations extends AsyncTask<Void, Void, List<Station>> {

    private WeakReference<Activity> activity;

    public GetAllStations(Activity activity){
        this.activity = new WeakReference<>(activity);
    }

    @Override
    protected List<Station> doInBackground(Void... params) {
        StationDao station_Dao = AppDatabase.getInstance(this.activity.get()).getStationDao();
        Log.d("info", "Getting all stations from database...");
        List<Station> stations = station_Dao.getAllStations();

        if(stations.size()>=1){ Log.d("info", "zwracam dane");return stations;}
        else{ Log.d("info", "nie zwracam danych"); return null;}
    }

    @Override
    protected void onPostExecute(List<Station> weather_data){
        Log.d("info", "Got all stations from database.");
    }
}
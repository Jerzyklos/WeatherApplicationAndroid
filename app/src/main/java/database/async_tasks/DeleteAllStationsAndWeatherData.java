package database.async_tasks;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import java.lang.ref.WeakReference;

import database.AppDatabase;
import database.daos.StationDao;
import database.daos.WeatherDataDao;

public class DeleteAllStationsAndWeatherData extends AsyncTask<Void, Void, Integer> {

    private WeakReference<Activity> activity;

    public DeleteAllStationsAndWeatherData(Activity activity){
        this.activity = new WeakReference<>(activity);
    }

    @Override
    protected Integer doInBackground(Void... params) {
        StationDao station_Dao = AppDatabase.getInstance(this.activity.get()).getStationDao();
        WeatherDataDao weather_data_Dao = AppDatabase.getInstance(this.activity.get()).getWeatherDataDao();

        weather_data_Dao.DeleteAllWeatherData();
        station_Dao.DeleteAllStations();

        return 1;
    }

    @Override
    protected void onPostExecute(Integer result){
        Log.d("info", "All data deleted.");
    }
}
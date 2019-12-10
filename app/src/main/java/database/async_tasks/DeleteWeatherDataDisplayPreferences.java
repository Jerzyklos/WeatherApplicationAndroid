package database.async_tasks;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import java.lang.ref.WeakReference;

import database.AppDatabase;
import database.daos.StationDao;
import database.daos.WeatherDataDao;
import database.daos.WeatherDataDisplayPreferencesDao;

public class DeleteWeatherDataDisplayPreferences extends AsyncTask<Void, Void, Integer> {

    private WeakReference<Activity> activity;

    public DeleteWeatherDataDisplayPreferences(Activity activity){
        this.activity = new WeakReference<>(activity);
    }

    @Override
    protected Integer doInBackground(Void... params) {
        WeatherDataDisplayPreferencesDao preferences_dao = AppDatabase.getInstance(this.activity.get()).getWeatherDataDisplayPreferencesDao();
        preferences_dao.DeleteWeatherDataDisplayPreferences();
        return 1;
    }

    @Override
    protected void onPostExecute(Integer result){
        Log.d("info", "Display preferences deleted.");
    }
}
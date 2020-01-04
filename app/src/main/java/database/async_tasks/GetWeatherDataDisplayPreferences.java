package database.async_tasks;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import java.lang.ref.WeakReference;

import database.AppDatabase;
import database.daos.WeatherDataDisplayPreferencesDao;
import database.entities.WeatherDataDisplayPreferences;

public class GetWeatherDataDisplayPreferences extends AsyncTask<Void, Void, WeatherDataDisplayPreferences> {

    private WeakReference<Activity> activity;

    public GetWeatherDataDisplayPreferences(Activity activity){
        this.activity = new WeakReference<>(activity);
    }

    @Override
    protected WeatherDataDisplayPreferences doInBackground(Void... params) {
        Log.d("info", "Getting default preferences..");
        WeatherDataDisplayPreferencesDao preferences_Dao = AppDatabase.getInstance(this.activity.get()).getWeatherDataDisplayPreferencesDao();
        WeatherDataDisplayPreferences preferences =  preferences_Dao.getWeatherDataDisplayPreferences();
        return preferences;
    }

    @Override
    protected void onPostExecute(WeatherDataDisplayPreferences preferences) {
        Log.d("info", "Got default preferences");
    }
}
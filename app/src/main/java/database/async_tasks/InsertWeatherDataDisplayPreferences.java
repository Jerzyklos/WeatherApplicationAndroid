package database.async_tasks;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import java.lang.ref.WeakReference;

import database.AppDatabase;
import database.daos.WeatherDataDisplayPreferencesDao;
import database.entities.WeatherDataDisplayPreferences;

public class InsertWeatherDataDisplayPreferences extends AsyncTask<Void, Void, Integer> {

    private WeakReference<Activity> activity;
    private WeatherDataDisplayPreferences preferences;

    public InsertWeatherDataDisplayPreferences(Activity activity, WeatherDataDisplayPreferences preferences){
        this.activity = new WeakReference<>(activity);
        this.preferences = preferences;
    }

    @Override
    protected Integer doInBackground(Void... params) {
        WeatherDataDisplayPreferencesDao preferences_Dao = AppDatabase.getInstance(this.activity.get()).getWeatherDataDisplayPreferencesDao();
        Log.d("info", "Inserting display preferences...");
        preferences_Dao.insert(preferences);
        return 1;
    }

    @Override
    protected void onPostExecute(Integer result){
        Log.d("info", "Inserted preferences");
    }
}

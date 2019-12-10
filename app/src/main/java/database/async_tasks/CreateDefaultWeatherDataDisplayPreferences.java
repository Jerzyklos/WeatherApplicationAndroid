package database.async_tasks;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import java.lang.ref.WeakReference;

import database.AppDatabase;
import database.daos.WeatherDataDisplayPreferencesDao;
import database.entities.WeatherDataDisplayPreferences;

public class CreateDefaultWeatherDataDisplayPreferences extends AsyncTask<Void, Void, WeatherDataDisplayPreferences> {

    private WeakReference<Activity> activity;

    public CreateDefaultWeatherDataDisplayPreferences(Activity activity){
        this.activity = new WeakReference<>(activity);
    }

    @Override
    protected WeatherDataDisplayPreferences doInBackground(Void... params) {
        WeatherDataDisplayPreferencesDao preferences_Dao = AppDatabase.getInstance(this.activity.get()).getWeatherDataDisplayPreferencesDao();
        Log.d("info", "creating default display preferences...");
        WeatherDataDisplayPreferences preferences = new WeatherDataDisplayPreferences(0, false, true, true, true, false, true, false, true, true, true, false);
        preferences_Dao.insert(preferences);
        return preferences;
    }

    @Override
    protected void onPostExecute(WeatherDataDisplayPreferences preferences){
        Log.d("info", "Created default preferences");
    }
}

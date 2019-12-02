package database.async_tasks;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import java.lang.ref.WeakReference;

import database.AppDatabase;
import database.daos.StationDao;
import database.daos.WeatherDataDao;
import database.entities.Station;
import database.entities.WeatherData;


public class InsertWeatherDataToDatabase extends AsyncTask<Void, Void, Integer> {

    private WeakReference<Activity> activity;
    WeatherData weather_data;

    public InsertWeatherDataToDatabase(Activity activity, WeatherData weather_data){
        this.activity = new WeakReference<>(activity);
        this.weather_data = weather_data;
    }

    @Override
    protected Integer doInBackground(Void... params) {
        WeatherDataDao weather_data_Dao = AppDatabase.getInstance(this.activity.get()).getWeatherDataDao();
        Log.d("info", "Inserting weather data to database...");
        weather_data_Dao.insert(weather_data);
        return 1;
    }

    @Override
    protected void onPostExecute(Integer result){Log.d("info", "Weather data inserted.");
    }
}

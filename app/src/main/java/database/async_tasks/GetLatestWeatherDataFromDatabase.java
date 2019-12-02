package database.async_tasks;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.List;

import database.AppDatabase;
import database.daos.StationDao;
import database.daos.WeatherDataDao;
import database.entities.Station;
import database.entities.WeatherData;

public class GetLatestWeatherDataFromDatabase extends AsyncTask<Void, Void, WeatherData> {

    private WeakReference<Activity> activity;
    private String station_id;

    public GetLatestWeatherDataFromDatabase(Activity activity, String station_id){
        this.activity = new WeakReference<>(activity);
        this.station_id = station_id;
    }

    @Override
    protected WeatherData doInBackground(Void... params) {
        WeatherDataDao weather_data_Dao = AppDatabase.getInstance(this.activity.get()).getWeatherDataDao();
        Log.d("info", "Getting weather data from database...");
        List<WeatherData> weather_data = weather_data_Dao.getGivenStationLatestWeatherData(station_id);
        if(weather_data.size()>=1){ Log.d("info", "zwracam dane");return weather_data.get(0);}
        else{ Log.d("info", "nie zwracam danych");return null;}
    }

    @Override
    protected void onPostExecute(WeatherData weather_data){
        Log.d("info", "Got weather data from database.");
    }
}

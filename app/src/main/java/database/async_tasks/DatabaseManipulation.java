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

public class DatabaseManipulation extends AsyncTask<Void, Void, Integer> {

    private WeatherData weather_data;
    private WeakReference<Activity> activity; // zczaić!

    public DatabaseManipulation(Activity activity, WeatherData weather_data) {
        this.activity = new WeakReference<>(activity);
        this.weather_data = weather_data;
    }

    @Override
    protected Integer doInBackground(Void... params) {
        WeatherDataDao weather_data_Dao = AppDatabase.getInstance(this.activity.get()).getWeatherDataDao();
        StationDao station_Dao = AppDatabase.getInstance(this.activity.get()).getStationDao();
        Station station =  station_Dao.getDefaultStation(); //TODO sprawdzic czy stacja z danym id istnieje.. no bo moze nie byc defaultowa
        if(station==null){ //TODO zlecić tworzenie defaultowej stacji innej funkcji/klasie
            Log.d("info", "no default station!");
            station = new Station("s000", "WFiIS AGH", 19.9129, 50.0670, 220, true);
            station_Dao.insert(station);
            weather_data_Dao.insert(weather_data);
        }
        else weather_data_Dao.insert(weather_data);

        return 1;
    }

    @Override
    protected void onPostExecute(Integer agentsCount) {
        if(agentsCount==1)Log.d("info", "chyba sie udalo...");
        else Log.d("info", "chyba sie nie udalo...");
    }
}
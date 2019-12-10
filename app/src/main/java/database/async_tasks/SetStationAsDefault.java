package database.async_tasks;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import java.lang.ref.WeakReference;

import database.AppDatabase;
import database.daos.StationDao;
import database.entities.Station;

public class SetStationAsDefault extends AsyncTask<Void, Void, Integer> {

    private WeakReference<Activity> activity;
    Station new_default_station;
    Station old_default_station;

    public SetStationAsDefault(Activity activity, Station new_default_station, Station old_default_station){
        this.activity = new WeakReference<>(activity);
        this.new_default_station = new_default_station;
        this.old_default_station = old_default_station;
    }

    @Override
    protected Integer doInBackground(Void... params) {
        StationDao station_Dao = AppDatabase.getInstance(this.activity.get()).getStationDao();
        Log.d("info", "Setting station as a default station...");
        if(old_default_station==null) station_Dao.update(new_default_station);
        else station_Dao.update(old_default_station, new_default_station);
        return 1;
    }

    @Override
    protected void onPostExecute(Integer result){Log.d("info", "Default station changed.");
    }
}

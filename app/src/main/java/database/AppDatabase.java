package database;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;
import database.daos.StationDao;
import database.daos.WeatherDataDao;
import database.daos.WeatherDataDisplayPreferencesDao;
import database.entities.Station;
import database.entities.WeatherData;
import database.entities.WeatherDataDisplayPreferences;

@Database(entities = {Station.class, WeatherData.class, WeatherDataDisplayPreferences.class}, version=5, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static final String DB_NAME = "AppDatabase.db";
    private static volatile AppDatabase instance;

    static synchronized public AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = create(context);
        }
        return instance;
    }

    private static AppDatabase create(final Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, DB_NAME).fallbackToDestructiveMigration().build();
    }

    public abstract StationDao getStationDao();
    public abstract WeatherDataDao getWeatherDataDao();
    public abstract WeatherDataDisplayPreferencesDao getWeatherDataDisplayPreferencesDao();
}



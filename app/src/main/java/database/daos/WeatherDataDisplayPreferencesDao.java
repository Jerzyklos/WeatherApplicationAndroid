package database.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import database.entities.WeatherDataDisplayPreferences;

@Dao
public interface WeatherDataDisplayPreferencesDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(WeatherDataDisplayPreferences preferences);

    @Update
    void update(WeatherDataDisplayPreferences... data);

    @Delete
    void delete(WeatherDataDisplayPreferences... data);

    @Query("DELETE FROM weather_data_display_preferences")
    void DeleteWeatherDataDisplayPreferences();

    @Query("SELECT * FROM weather_data_display_preferences WHERE id=0")
    WeatherDataDisplayPreferences getWeatherDataDisplayPreferences();

}

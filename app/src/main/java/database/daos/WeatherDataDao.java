package database.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

import database.entities.WeatherData;

@Dao
public interface WeatherDataDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(WeatherData data);

    @Update
    void update(WeatherData... data);

    @Delete
    void delete(WeatherData... data);

    @Query("SELECT * FROM weather_data WHERE station_id=:station_id")
    List<WeatherData> getGivenStationWeatherData(String station_id);

    @Query("SELECT * FROM weather_data")
    List<WeatherData> getAll();

    @Query("SELECT * FROM weather_data ORDER BY datetime(utc_time) AND station_id=:station_id")
    List<WeatherData> getGivenStationLatestWeatherData(String station_id);
}

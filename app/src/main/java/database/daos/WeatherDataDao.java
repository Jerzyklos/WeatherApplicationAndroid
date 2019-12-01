package database.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

import database.entities.WeatherData;

@Dao
public interface WeatherDataDao {
    @Insert
    void insert(WeatherData data);

    @Update
    void update(WeatherData... data);

    @Delete
    void delete(WeatherData... data);

    @Query("SELECT * FROM weather_data WHERE station_id=:station_id")
    List<WeatherData> getGivenStationWeatherData(String station_id);
}

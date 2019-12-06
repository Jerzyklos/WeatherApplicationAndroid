package database.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

import database.entities.Station;
import database.entities.WeatherData;

@Dao
public interface StationDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Station station);

    @Update
    void update(Station... station);

    @Delete
    void delete(Station... station);

    @Query("SELECT * FROM stations")
    List<Station> getAllStations();

    @Query("SELECT * FROM stations WHERE id=:id")
    Station getGivenStation(String id);

    @Query("SELECT * FROM stations WHERE default_station= 1")
    Station getDefaultStation();
}
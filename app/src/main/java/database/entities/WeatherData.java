package database.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

@Entity(primaryKeys = {"utc_time", "station_id"},
        tableName = "weather_data",
        foreignKeys = @ForeignKey(entity = Station.class,
                                  parentColumns = "id",
                                  childColumns = "station_id"),
                                  indices = {@Index("station_id")}
        )
public class WeatherData {
    @NonNull
    public String utc_time;
    @NonNull
    public String station_id;
    public String local_time;
    public double pressure;
    public double temperature;
    public double dew_point_temperature; //do jakiego w nocy minimalnie spada
    public double humidity; // w procentach
    public double rainfall_intensity_in_last_hour;
    public double rainfall_intensity;
    public double wind_direction; //od polnocy w prawo
    public double wind_speed; //srednia
    public double wind_speed_current; //porywy
    public double meters_above_sea_level; //bez

    public WeatherData(String utc_time, String station_id, String local_time, double pressure, double temperature,
                       double dew_point_temperature, double humidity, double rainfall_intensity_in_last_hour,
                       double rainfall_intensity, double wind_direction, double wind_speed,
                         double wind_speed_current, double meters_above_sea_level){
        this.utc_time = utc_time;
        this.station_id = station_id;
        this.local_time = local_time;
        this.pressure = pressure;
        this.temperature = temperature;
        this.dew_point_temperature = dew_point_temperature;
        this.humidity = humidity;
        this.rainfall_intensity_in_last_hour = rainfall_intensity_in_last_hour;
        this.rainfall_intensity = rainfall_intensity;
        this.wind_direction = wind_direction;
        this.wind_speed = wind_speed;
        this.wind_speed_current = wind_speed_current;
        this.meters_above_sea_level = meters_above_sea_level;
    }
}

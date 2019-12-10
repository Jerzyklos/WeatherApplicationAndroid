package database.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "weather_data_display_preferences")
public class WeatherDataDisplayPreferences {
    @PrimaryKey
    @NonNull
    public int id;
    public boolean local_time;
    public boolean utc_time;
    public boolean pressure;
    public boolean temperature;
    public boolean dew_point_temperature; //do jakiego w nocy minimalnie spada
    public boolean humidity; // w procentach
    public boolean rainfall_intensity_in_last_hour;
    public boolean rainfall_intensity;
    public boolean wind_direction; //od polnocy w prawo
    public boolean wind_speed; //srednia
    public boolean wind_speed_current; //porywy

    public WeatherDataDisplayPreferences(int id, boolean utc_time, boolean local_time, boolean pressure, boolean temperature,
                                         boolean dew_point_temperature, boolean humidity, boolean rainfall_intensity_in_last_hour,
                                         boolean rainfall_intensity, boolean wind_direction, boolean wind_speed, boolean wind_speed_current){
        this.id = id;
        this.utc_time = utc_time;
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
    }
}


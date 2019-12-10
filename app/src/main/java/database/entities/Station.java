package database.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "stations")
public class Station {
    @PrimaryKey @NonNull
    public String id;
    public String name;
    public boolean is_default_station;
    public double longitude;
    public double latitude;
    public double altitude;

    public Station(String id, String name, double longitude, double latitude, double altitude, boolean is_default_station){
        this.id=id;
        this.name=name;
        this.longitude=longitude;
        this.latitude=latitude;
        this.altitude=altitude;
        this.is_default_station=is_default_station;
    }
}


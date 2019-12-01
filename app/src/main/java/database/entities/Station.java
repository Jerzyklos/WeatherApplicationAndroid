package database.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "stations")
public class Station {
    @PrimaryKey @NonNull
    public String id;
    public String name;
    public boolean default_station;
    public double longitude;
    public double latitude;
    public double altitude;

    //TODO dwa konstruktory
//    public Station(String id, String name, double longitude, double latitude, double altitude){
//        this.id=id;
//        this.name=name;
//        this.longitude=longitude;
//        this.latitude=latitude;
//        this.altitude=altitude;
//        this.default_station=false;
//    }
    public Station(String id, String name, double longitude, double latitude, double altitude, boolean default_station){
        this.id=id;
        this.name=name;
        this.longitude=longitude;
        this.latitude=latitude;
        this.altitude=altitude;
        this.default_station=default_station;
    }
}


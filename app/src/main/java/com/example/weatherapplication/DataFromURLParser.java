package com.example.weatherapplication;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import database.entities.Station;
import database.entities.WeatherData;

public class DataFromURLParser {
    public List<Double> ParseJSONForChartData(String request_data, String json_key_param){
        List<Double> list = new ArrayList<>();
        try {
            JSONArray array = new JSONArray(request_data);
            if(array.length()!=0){
                for (int i = 0; i < array.length(); i++){
                    list.add(array.getJSONObject(i).getJSONObject("data").getDouble(json_key_param));
                }
            }
        }
        catch(JSONException e){Log.d("error", "Caught JSON exception");}
        return list;
    }
    public List<Station> ParseJSONForStationsData(String request_data){
        List<Station> stations = new ArrayList<>();
        try {
            JSONArray array = new JSONArray(request_data);
            for(int i=0; i<array.length(); i++){
                JSONObject data = array.getJSONObject(i);
                String station_id = data.getString("station");
                String name = data.getString("name");
                Double longitude = Double.parseDouble(data.getString("long"));
                Double latitude = Double.parseDouble(data.getString("lati"));
                Double altitude = Double.parseDouble(data.getString("alti"));
                stations.add(new Station(station_id, name, longitude, latitude, altitude, false));
            }
        }
        catch (JSONException e){Log.d("error","Caught JSON exception");}
        return stations;
    }
    public WeatherData ParseJSONForWeatherData(String request_data){
        WeatherData weather_data = null;
        try {
            JSONArray array = new JSONArray(request_data);
            JSONObject whole_data = array.getJSONObject(0);
            String station_id = whole_data.getString("station");
            String utc_time = whole_data.getString("utc");
            String local_time = whole_data.getString("time");

            JSONObject json_data = whole_data.getJSONObject("data");
            // value -999.0 means that data is null
            Double temp = -999.0;
            if(json_data.getString("ta")!="null") temp = Double.parseDouble(json_data.getString("ta"));
            Double pressure = -999.0;
            if(json_data.getString("p0")!="null") pressure = Double.parseDouble(json_data.getString("p0"));
            Double dew_temp = -999.0;
            if(json_data.getString("t0")!="null") dew_temp = Double.parseDouble(json_data.getString("t0"));
            Double hum = -999.0;
            if(json_data.getString("ha")!="null") hum = Double.parseDouble(json_data.getString("ha"));
            Double rain_last_hour = -999.0;
            if(json_data.getString("r1")!="null") rain_last_hour = Double.parseDouble(json_data.getString("r1"));
            Double rain = -999.0;
            if(json_data.getString("ra")!="null") rain = Double.parseDouble(json_data.getString("ra"));
            Double wind_dir = -999.0;
            if(json_data.getString("wd")!="null") wind_dir = Double.parseDouble(json_data.getString("wd"));
            Double wind_sp = -999.0;
            if(json_data.getString("ws")!="null") wind_sp = Double.parseDouble(json_data.getString("ws"));
            Double wind_sp_curr = -999.0;
            if(json_data.getString("wg")!="null") wind_sp_curr = Double.parseDouble(json_data.getString("wg"));
            Double meters_ab_sea = -999.0;
            if(json_data.getString("h0")!="null") meters_ab_sea = Double.parseDouble(json_data.getString("h0"));

            weather_data = new WeatherData(utc_time, station_id, local_time, pressure, temp, dew_temp,
                    hum, rain_last_hour, rain, wind_dir, wind_sp, wind_sp_curr, meters_ab_sea);
        }
        catch (JSONException e){Log.d("error","Caught JSON exception");}

        return weather_data;
    }

}


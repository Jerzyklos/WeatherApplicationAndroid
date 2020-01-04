package com.example.weatherapplication;

import android.util.Log;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;

import database.entities.Station;
import database.entities.WeatherData;

import static org.junit.Assert.assertEquals;

public class GetDataFromURLUnitTest {
    @Test
    public void GetStationsFromURLRequest_isCorrect(){
        String url = "http://mech.fis.agh.edu.pl/meteo/rest/json/info/";
        StringBuilder request_builder = new StringBuilder();
        try{
            InputStream is = new URL(url).openStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String line="";
            while(line!=null){
                line = rd.readLine();
                request_builder.append(line);
            }
        }
        catch(MalformedURLException e){Log.d("error", "Malformed URL Exception occured.");}
        catch(IOException e){Log.d("error", "IOException occured.");}

        String request_data=request_builder.toString();
        DataFromURLParser parser = new DataFromURLParser();
        List<Station> stations = parser.ParseJSONForStationsData(request_data);
        assertEquals(true, stations.size()!=0);
    }
    @Test
    public void GetWeatherDataFromURLRequest_isCorrect(){
        String url = "http://mech.fis.agh.edu.pl/meteo/rest/json/last/s000";
        StringBuilder request_builder = new StringBuilder();
        try{
            InputStream is = new URL(url).openStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String line="";
            while(line!=null){
                line = rd.readLine();
                request_builder.append(line);
            }
        }
        catch(MalformedURLException e){Log.d("error", "Malformed URL Exception occured.");}
        catch(IOException e){Log.d("error", "IOException occured.");}

        String request_data=request_builder.toString();
        DataFromURLParser parser = new DataFromURLParser();
        WeatherData weather_data = parser.ParseJSONForWeatherData(request_data);
        assertEquals(true, weather_data!=null);
    }
    @Test
    public void GetWeatherDataForChartFromURLRequest_isCorrect(){
        String url = "http://mech.fis.agh.edu.pl/meteo/rest/json/temp/s000/2020-01-01/2020-01-01";
        StringBuilder request_builder = new StringBuilder();
        try{
            InputStream is = new URL(url).openStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String line="";
            while(line!=null){
                line = rd.readLine();
                request_builder.append(line);
            }
        }
        catch(MalformedURLException e){Log.d("error", "Malformed URL Exception occured.");}
        catch(IOException e){Log.d("error", "IOException occured.");}

        String request_data = request_builder.toString();
        DataFromURLParser parser = new DataFromURLParser();
        List<Double> list = parser.ParseJSONForChartData(request_data, "ta");
        assertEquals(true, list.size()!=0);
    }
}

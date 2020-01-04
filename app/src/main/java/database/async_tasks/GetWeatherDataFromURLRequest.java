package database.async_tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.example.weatherapplication.DataFromURLParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

import database.entities.WeatherData;

public class GetWeatherDataFromURLRequest extends AsyncTask<Void, Integer, WeatherData> {
    private String url;

    public GetWeatherDataFromURLRequest(String station_id){
        this.url = "http://mech.fis.agh.edu.pl/meteo/rest/json/last/"+station_id;
    }

    @Override
    protected void onPreExecute(){}

    @Override
    protected WeatherData doInBackground(Void... params){
        Log.d("Info", "Retrieving data...");

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
        return weather_data;
    }

    @Override
    protected void onPostExecute(WeatherData weather_data) {
        Log.d("info", "Data retrieved.");
    }
}
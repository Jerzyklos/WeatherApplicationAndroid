package database.async_tasks;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.example.weatherapplication.DataFromURLParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import database.entities.Station;
import database.entities.WeatherData;

public class GetStationsFromURLRequest extends AsyncTask<Void, Integer, List<Station>> {
    private String url;

    public GetStationsFromURLRequest(){
        this.url = "http://mech.fis.agh.edu.pl/meteo/rest/json/info/";
    }

    @Override
    protected void onPreExecute(){}

    @Override
    protected List<Station> doInBackground(Void... params){
        Log.d("Info", "Retrieving stations data...");

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
        return stations;
    }

    @Override
    protected void onPostExecute(List<Station> stations) {
        Log.d("info", "stations retrieved.");
    }
}
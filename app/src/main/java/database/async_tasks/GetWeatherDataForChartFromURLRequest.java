package database.async_tasks;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.example.weatherapplication.DataFromURLParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;

public class GetWeatherDataForChartFromURLRequest extends AsyncTask<Void, Integer, List<Double>> {
    private String url;
    private String json_key_param;

    public GetWeatherDataForChartFromURLRequest(String station_id, String weather_parameter, String date){
        this.url = "http://mech.fis.agh.edu.pl/meteo/rest/json/"+weather_parameter+"/"+station_id+"/"+date+"/"+date;
        Log.d("info", "pobieram!");
        json_key_param = null;
        if(weather_parameter=="temp") json_key_param ="ta";
        if(weather_parameter=="temp0") json_key_param ="t0";
        if(weather_parameter=="pres0") json_key_param ="p0";
        if(weather_parameter=="humi") json_key_param ="ha";
        if(weather_parameter=="rain1") json_key_param ="r1";
        if(weather_parameter=="rain") json_key_param ="ra";
        if(weather_parameter=="windd") json_key_param ="wd";
        if(weather_parameter=="winds") json_key_param ="ws";
        if(weather_parameter=="windg") json_key_param ="wg";
    }

    @Override
    protected void onPreExecute(){}

    @Override
    protected List<Double> doInBackground(Void... params){
        Log.d("Info", "Retrieving data for chart...");
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
        List<Double> list = parser.ParseJSONForChartData(request_data, json_key_param);
        return list;
    }

    @Override
    protected void onPostExecute(List<Double> list) {
        Log.d("info", "Data for chart retrieved.");
    }
}
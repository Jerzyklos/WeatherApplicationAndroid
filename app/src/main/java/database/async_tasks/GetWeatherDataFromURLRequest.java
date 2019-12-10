package database.async_tasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

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

import database.entities.WeatherData;

public class GetWeatherDataFromURLRequest extends AsyncTask<Void, Integer, WeatherData> {

    private WeakReference<Activity> activity;
    //public ProgressDialog progress_dialog;
    private String url;

    public GetWeatherDataFromURLRequest(Activity activity, String station_id){
        this.activity = new WeakReference<>(activity);
        this.url = "http://mech.fis.agh.edu.pl/meteo/rest/json/last/"+station_id;
        Log.d("info", "pobieram!");
    }

    @Override
    protected void onPreExecute(){
//        progress_dialog = new ProgressDialog(this.activity.get());
//        progress_dialog.setMessage("Loading...");
//        progress_dialog.show();
    }

    @Override
    protected WeatherData doInBackground(Void... params){
        Log.d("Info", "Retrieving data...");
        String request_data="";
        try{
            InputStream is = new URL(url).openStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String line="";
            while(line!=null){
                line = rd.readLine();
                request_data += line + "\n";
            }
        }
        catch(MalformedURLException e){Log.d("error", "Malformed URL Exception occured.");}
        catch(IOException e){Log.d("error", "IOException occured.");}

        Log.d("info", request_data);

        WeatherData weather_data = null;
        try {
            // TODO przeniesć parsowanie jsona do innej klasy
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
            if(json_data.getString("wg")!="null") wind_sp = Double.parseDouble(json_data.getString("wg"));
            Double wind_sp_curr = -999.0;
            if(json_data.getString("ws")!="null") wind_sp_curr = Double.parseDouble(json_data.getString("ws"));
            Double meters_ab_sea = -999.0;
            if(json_data.getString("h0")!="null") meters_ab_sea = Double.parseDouble(json_data.getString("h0"));

            weather_data = new WeatherData(utc_time, station_id, local_time, pressure, temp, dew_temp,
                    hum, rain_last_hour, rain, wind_dir, wind_sp, wind_sp_curr, meters_ab_sea);
        }
        catch (JSONException e){Log.d("error","Caught JSON exception");}

        return weather_data;
    }

    @Override
    protected void onPostExecute(WeatherData weather_data) {
        Log.d("info", "Data retrieved.");
    }
}
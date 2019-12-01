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

public class GetDataFromURLRequest extends AsyncTask<String, Integer, WeatherData> {

    private WeakReference<Activity> activity;
    private ProgressDialog progress_dialog;

    public GetDataFromURLRequest(Activity activity){
        this.activity = new WeakReference<>(activity);
    }

    @Override
    protected void onPreExecute(){
        progress_dialog = new ProgressDialog(this.activity.get());
        progress_dialog.setMessage("Loading...");
        progress_dialog.show();
    }

    @Override
    protected WeatherData doInBackground(String... urls){
        Log.d("Info", "Retrieving data...");
        String request_data="";
        try{
            InputStream is = new URL(urls[0]).openStream();
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
            JSONArray array = new JSONArray(request_data);
            JSONObject whole_data = array.getJSONObject(0);
            String station_id = whole_data.getString("station");
            String time = whole_data.getString("utc");

            JSONObject json_data = whole_data.getJSONObject("data");

            Double temp = Double.parseDouble(json_data.getString("ta"));
            Double pressure = Double.parseDouble(json_data.getString("p0"));
            Double dew_temp = Double.parseDouble(json_data.getString("t0"));
            Double hum = Double.parseDouble(json_data.getString("ha"));
            Double rain_last_hour = Double.parseDouble(json_data.getString("r1"));
            Double rain = Double.parseDouble(json_data.getString("ra"));
            Double wind_dir = Double.parseDouble(json_data.getString("wd"));
            Double wind_sp = Double.parseDouble(json_data.getString("wg"));
            Double wind_sp_curr = Double.parseDouble(json_data.getString("ws"));
            Double meters_ab_sea = Double.parseDouble(json_data.getString("h0"));

            weather_data = new WeatherData(time, station_id, pressure, temp, dew_temp,
                    hum, rain_last_hour, rain, wind_dir, wind_sp, wind_sp_curr, meters_ab_sea);
        }
        catch (JSONException e){Log.d("error","Caught JSON exception");}

        return weather_data;
    }

    @Override
    protected void onPostExecute(WeatherData weather_data) {
        progress_dialog.dismiss();
        Log.d("info", "Data retrieved.");
    }
}
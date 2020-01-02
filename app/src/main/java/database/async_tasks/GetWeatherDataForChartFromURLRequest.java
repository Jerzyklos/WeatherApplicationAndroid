package database.async_tasks;

import android.app.Activity;
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
import java.util.ArrayList;
import java.util.List;

public class GetWeatherDataForChartFromURLRequest extends AsyncTask<Void, Integer, List<Double>> {

    private WeakReference<Activity> activity;
    private String url;
    private String param;

    public GetWeatherDataForChartFromURLRequest(Activity activity, String station_id, String weather_parameter, String date){
        this.activity = new WeakReference<>(activity);
        this.url = "http://mech.fis.agh.edu.pl/meteo/rest/json/"+weather_parameter+"/"+station_id+"/"+date+"/"+date;
        Log.d("info", "pobieram!");
        param = null;
        if(weather_parameter=="temp") param="ta";
        if(weather_parameter=="temp0") param="t0";
        if(weather_parameter=="pres0") param="p0";
        if(weather_parameter=="humi") param="ha";
        if(weather_parameter=="rain1") param="r1";
        if(weather_parameter=="rain") param="ra";
        if(weather_parameter=="windd") param="wd";
        if(weather_parameter=="winds") param="ws";
        if(weather_parameter=="windg") param="wg";
    }

    @Override
    protected void onPreExecute(){}

    @Override
    protected List<Double> doInBackground(Void... params){
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

        List<Double> list = null;
        //when data set is not empty
        if(request_data!="[]") {
            try {
                // TODO przeniesć parsowanie jsona do innej klasy
                list = new ArrayList<>();
                JSONArray array = new JSONArray(request_data);
                for (int i = 0; i < array.length(); i++){
                    list.add(array.getJSONObject(i).getJSONObject("data").getDouble(param));
                }
            } catch (JSONException e){ Log.d("error", "Caught JSON exception");}
        }
        return list;
    }

    @Override
    protected void onPostExecute(List<Double> list) {
        Log.d("info", "Data retrieved.");
        for(Double el : list){
            Log.d("value", Double.toString(el));
        }
    }
}
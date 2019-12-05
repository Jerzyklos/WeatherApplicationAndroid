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

import database.entities.Station;
import database.entities.WeatherData;

public class GetStationsFromURLRequest extends AsyncTask<Void, Integer, List<Station>> {

    private WeakReference<Activity> activity;
    //    private ProgressDialog progress_dialog;
    private String url;

    public GetStationsFromURLRequest(Activity activity){
        this.activity = new WeakReference<>(activity);
        this.url = "http://mech.fis.agh.edu.pl/meteo/rest/json/info/";
        Log.d("info", "pobieram!");
    }

    @Override
    protected void onPreExecute(){
//        progress_dialog = new ProgressDialog(this.activity.get());
//        progress_dialog.setMessage("Loading...");
//        progress_dialog.show();
    }

    @Override
    protected List<Station> doInBackground(Void... params){
        Log.d("Info", "Retrieving stations...");
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
        //TODO zlecic parsowanie jsonów(tu i w weather data) innej klasie
        return stations;
    }

    @Override
    protected void onPostExecute(List<Station> stations) {
//        progress_dialog.dismiss();
        Log.d("info", "stations retrieved.");
    }
}
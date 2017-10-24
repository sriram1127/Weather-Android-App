package com.example.sriram.weatherapp;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.sriram.hw5_weatherapp.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by SRIRAM on 08-10-2016.
 */
public class WeatherBrowser extends AsyncTask<String, Void, List<Weather>> {

    private CityWeatherInterface cityWeatherInterface;
    private List<Weather> hourlyWeather = null;
    private ProgressDialog progressDialog = null;

    public WeatherBrowser(CityWeatherInterface cityWeatherInterface) {
        this.cityWeatherInterface = cityWeatherInterface;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = cityWeatherInterface.getProgressDialog();
        progressDialog.setTitle(R.string.dialog_title);
    }

    @Override
    protected void onPostExecute(List<Weather> weathers) {
        if (weathers != null && weathers.size() > 0) {
            ((LinearLayout) cityWeatherInterface.getLocationView()).setVisibility(View.VISIBLE);
            cityWeatherInterface.setHourlyWeather(weathers);
            ListView hourlyLV = cityWeatherInterface.getHourlyLV();
            WeatherAdapter weatherAdapter = new WeatherAdapter((CityWeatherActivity) cityWeatherInterface, R.layout.weatherlayout, weathers);
            weatherAdapter.setNotifyOnChange(true);
            hourlyLV.setAdapter(weatherAdapter);
            progressDialog.dismiss();
        } else {
            cityWeatherInterface.setFavoriteAdd(false);
            Toast.makeText((CityWeatherActivity) cityWeatherInterface, "No cities match your query", Toast.LENGTH_SHORT).show();
            Handler mHandler = new Handler();
            mHandler.postDelayed(new Runnable() {

                @Override
                public void run() {
                    ((CityWeatherActivity) cityWeatherInterface).finish();
                }

            }, 5000L);

        }
    }

    @Override
    protected List<Weather> doInBackground(String... params) {
        String line = null;
        try {
            URL url = new URL(params[0]);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.connect();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuilder sb = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            String json = sb.toString();
            if (!json.contains("error")) {
                hourlyWeather = WeatherParser.parseWeather(json);
            }
            /*for (Weather weather: hourlyWeather) {

            }*/
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return hourlyWeather;
    }
}

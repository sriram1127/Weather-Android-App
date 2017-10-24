package com.example.sriram.weatherapp;

import android.app.ProgressDialog;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.List;

/**
 * Created by SRIRAM on 08-10-2016.
 */
public interface CityWeatherInterface {

    public void setFavoriteAdd(boolean favoriteAdd);

    public ProgressDialog getProgressDialog();

    public List<Weather> getHourlyWeather();

    public void setHourlyWeather(List<Weather> hourlyWeather);

    public ListView getHourlyLV();

    public LinearLayout getLocationView();

    public void setHourlyLV(ListView hourlyLV);
}

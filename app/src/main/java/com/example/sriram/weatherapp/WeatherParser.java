package com.example.sriram.weatherapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SRIRAM on 08-10-2016.
 */
public class WeatherParser {


    public static List<Weather> parseWeather(String io) {
        List<Weather> hourlyWeather = new ArrayList<Weather>();
        String farenheit = "°F";
        Integer minTemp = null;
        Integer maxTemp = null;
        Weather weather = null;
        JSONObject weatherObject = null;
        JSONObject fcttime = null;
        JSONObject feelsLike = null;
        JSONObject dewpoint = null;
        JSONObject mslp = null;
        JSONObject temp = null;
        JSONObject wspd = null;
        JSONObject wdir = null;
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(io);

            JSONArray hourlyForecast = jsonObject.getJSONArray("hourly_forecast");
            for (int i = 0; i < hourlyForecast.length(); i++) {
                weatherObject = hourlyForecast.getJSONObject(i);
                weather = new Weather();
                fcttime = weatherObject.getJSONObject("FCTTIME");
                weather.setTime(fcttime.getString("civil").toLowerCase());
                temp = weatherObject.getJSONObject("temp");
                int hourTemperature = Integer.parseInt(temp.getString("english"));
                if (minTemp == null && maxTemp == null) {
                    minTemp = hourTemperature;
                    maxTemp = hourTemperature;
                } else {
                    minTemp = minTemp < hourTemperature ? minTemp : hourTemperature;
                    maxTemp = maxTemp > hourTemperature ? maxTemp : hourTemperature;
                }
                weather.setTemperature(hourTemperature + farenheit);
                dewpoint = weatherObject.getJSONObject("dewpoint");
                weather.setDewPoint(dewpoint.getString("english"));
                weather.setCloud(weatherObject.getString("condition"));
                weather.setIconUrl(weatherObject.getString("icon_url"));
                wspd = weatherObject.getJSONObject("wspd");
                weather.setWindSpeed(wspd.getString("english"));
                wdir = weatherObject.getJSONObject("wdir");
                weather.setWindDirection(wdir.getString("dir"));
                weather.setClimateType(weatherObject.getString("wx"));
                weather.setHumidity(weatherObject.getString("humidity"));
                feelsLike = weatherObject.getJSONObject("feelslike");
                weather.setFeelsLike(feelsLike.getString("english"));
                mslp = weatherObject.getJSONObject("mslp");
                weather.setPressure(mslp.getString("metric"));
                hourlyWeather.add(weather);
            }
            for (Weather weatherObj : hourlyWeather) {
                weatherObj.setMaximumTemp(maxTemp.toString());
                weatherObj.setMinimumTemp(minTemp.toString());
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return hourlyWeather;
    }


}

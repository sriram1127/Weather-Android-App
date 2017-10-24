package com.example.sriram.weatherapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sriram.hw5_weatherapp.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class CityWeatherDetailActivity extends AppCompatActivity {

    private TextView currentLocation = null;
    private TextView temperatureinF = null;
    private TextView maxTemp = null;
    private TextView minTemp = null;
    private TextView feelsLike = null;
    private TextView humidity = null;
    private TextView dewpoint = null;
    private TextView pressure = null;
    private TextView clouds = null;
    private TextView winds = null;
    private ImageView cloudImage = null;
    private TextView temperature = null;
    private TextView cloudDescritpion = null;
    private String state = null;
    private String city = null;
    private Weather weather = null;
    private SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy");
    private Gson gson = new Gson();
   // private String CurrentTemp = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_weather_detail);
        final String farenheit = " Farenheit";
        final String percentage = " %";
        final String pressureMetric = " hPa";
        setTitle("City Weather");

        currentLocation = (TextView) findViewById(R.id.currentLocation);
        cloudImage = (ImageView) findViewById(R.id.cloudImage);
        temperatureinF = (TextView) findViewById(R.id.temperature);
        cloudDescritpion = (TextView) findViewById(R.id.cloudDescription);
        maxTemp = (TextView) findViewById(R.id.maxTemp);
        minTemp = (TextView) findViewById(R.id.minTemp);
        feelsLike = (TextView) findViewById(R.id.feelsLike);
        humidity = (TextView) findViewById(R.id.humidity);
        dewpoint = (TextView) findViewById(R.id.dewpoint);
        pressure = (TextView) findViewById(R.id.pressure);
        clouds = (TextView) findViewById(R.id.clouds);
        winds = (TextView) findViewById(R.id.winds);

        if (getIntent().getExtras() != null) {
            weather = (Weather) getIntent().getExtras().get("weather");
            city = (String) getIntent().getExtras().get("city");
            state = (String) getIntent().getExtras().get("state");
            //CurrentTemp = (String) getIntent().getExtras().get("currentTemperature");
            String locationAndTime = city + ", " + state;
            currentLocation.setText(locationAndTime);
            Picasso.with(this).load(weather.getIconUrl()).into(cloudImage);
            temperatureinF.setText(weather.getTemperature());
            cloudDescritpion.setText(weather.getCloud());
            maxTemp.setText(weather.getMaximumTemp() + farenheit);
            minTemp.setText(weather.getMinimumTemp() + farenheit);
            feelsLike.setText(weather.getFeelsLike() + farenheit);
            humidity.setText(weather.getHumidity() + percentage);
            dewpoint.setText(weather.getDewPoint() + farenheit);
            pressure.setText(weather.getPressure() + pressureMetric);
            clouds.setText(weather.getCloud());
            winds.setText(weather.getWindSpeed() + "mph, " + weather.getWindDirection() + "°West");


        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        SharedPreferences mPrefs = getSharedPreferences("favoritesFile", MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //noinspection SimplifiableIfStatement
        Log.d("check " + R.id.action_settings, item.getItemId() + "");
        if (item.getItemId() == R.id.action_settings) {
            List<Favorite> favoriteList = null;
            String json = mPrefs.getString("favorites", null);
            Favorite favorite = new Favorite();
            favorite.setCity(city);
            favorite.setState(state);
            favorite.setTemperature(weather.getTemperature());
            favorite.setUpdatedDate(sdf.format(new Date()));

            if (json == null) {
                favoriteList = new ArrayList<Favorite>();
                favoriteList.add(favorite);
                json = gson.toJson(favoriteList);
                prefsEditor.clear();
                prefsEditor.putString("favorites", json);
            } else {
                json = mPrefs.getString("favorites", "");
                Type type = new TypeToken<List<Favorite>>() {
                }.getType();
                favoriteList = gson.fromJson(json, type);
                Iterator<Favorite> iterator = favoriteList.iterator();
                while (iterator.hasNext()) {
                    if (iterator.next().getCity().equalsIgnoreCase(city)) {
                        iterator.remove();
                    }
                }
                favoriteList.add(favorite);
                json = gson.toJson(favoriteList);
                prefsEditor.clear();
                prefsEditor.putString("favorites", json);
            }
            prefsEditor.commit();
        }
        Toast.makeText(this, "City added to favorites", Toast.LENGTH_LONG).show();
        return true;
    }
}

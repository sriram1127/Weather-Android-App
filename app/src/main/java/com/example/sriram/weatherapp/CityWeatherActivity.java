package com.example.sriram.weatherapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sriram.hw5_weatherapp.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class CityWeatherActivity extends AppCompatActivity implements CityWeatherInterface {

    private String city = null;
    private String state = null;
    private ProgressDialog progressDialog = null;
    private TextView location = null;
    private List<Weather> hourlyWeather = null;
    private ListView hourlyLV = null;
    private LinearLayout locationView = null;
    private Weather weather = null;

    public void setFavoriteAdd(boolean favoriteAdd) {
        this.favoriteAdd = favoriteAdd;
    }

    private Gson gson = new Gson();
    private SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy");
    private boolean favoriteAdd = true;

    public LinearLayout getLocationView() {
        return locationView;
    }

    public List<Weather> getHourlyWeather() {
        return hourlyWeather;
    }

    public void setHourlyWeather(List<Weather> hourlyWeather) {
        this.hourlyWeather = hourlyWeather;
    }

    public ProgressDialog getProgressDialog() {
        return progressDialog;
    }

    public ListView getHourlyLV() {
        return hourlyLV;
    }

    public void setHourlyLV(ListView hourlyLV) {
        this.hourlyLV = hourlyLV;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        progressDialog = new ProgressDialog(this);
        setContentView(R.layout.activity_city_weather);
        setTitle("City Weather");
        if (getIntent().getExtras() != null) {
            city = (String) getIntent().getExtras().get("city");
            state = (String) getIntent().getExtras().get("state");
            location = (TextView) findViewById(R.id.currentCityAndState);
            location.setText(city + ", " + state.toUpperCase());
        }
        locationView = (LinearLayout) findViewById(R.id.locationView);
        String url = "http://api.wunderground.com/api/d26f5add407be857/hourly/q/" + state + "/" + city.replace(" ", "_") + ".json";
        Log.d("url Demo", url);
        new WeatherBrowser(this).execute(url);
        hourlyLV = (ListView) findViewById(R.id.cityWeatherLV);



        /*for (int i = 0; i < hourlyWeather.size();i++)
        {
            Weather weather = hourlyWeather.get(i);

        }*/
        hourlyLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showDetails(position);
            }
        });
    }

    private void showDetails(int position) {
        if (hourlyWeather != null && hourlyWeather.size() > 0) {
            weather = hourlyWeather.get(position);
            Intent intent = new Intent(this, CityWeatherDetailActivity.class);
            intent.putExtra("weather", weather);
           // intent.putExtra("currentTemperature", hourlyWeather.get(0).getTemperature());
            intent.putExtra("city", city);
            intent.putExtra("state", state);
            startActivity(intent);
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
        if(favoriteAdd) {
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
                favorite.setTemperature(hourlyWeather.get(0).getTemperature());
                favorite.setUpdatedDate(sdf.format(new Date()));

                if (json == null) {
                    favoriteList = new ArrayList<Favorite>();
                    favoriteList.add(favorite);
                    json = gson.toJson(favoriteList);
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
                    prefsEditor.putString("favorites", json);
                }
                prefsEditor.commit();
            }
            Toast.makeText(this, "City added to favorites", Toast.LENGTH_LONG).show();
            return true;
        }
        Toast.makeText(this, "City cannot be added to favorites", Toast.LENGTH_LONG).show();
        return true;
    }

}

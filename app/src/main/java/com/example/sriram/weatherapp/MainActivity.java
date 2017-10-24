package com.example.sriram.weatherapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sriram.hw5_weatherapp.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainActivityInterface, View.OnClickListener {

    private ProgressDialog progressDialog = null;
    private EditText cityTV = null;
    private EditText stateTV = null;
    private String city = null;
    private String state = null;
    private TextView msg = null;
    private ListView favorites = null;
    private TextView favoriteLabel = null;
    private List<Favorite> favoriteList = null;
    private FavoriteListAdapter favoriteListAdapter = null;
    private SharedPreferences mPrefs = null;
    private Gson gson = null;
    private SharedPreferences.Editor prefsEditor = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Weather App");
        cityTV = (EditText) findViewById(R.id.city);
        stateTV = (EditText) findViewById(R.id.state);
        favorites = (ListView) findViewById(R.id.favorites);
        msg = (TextView) findViewById(R.id.noFavoriteMsg);
        favorites.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                removeCity(favoriteList.get(position));
                return true;
            }
        });
    }

    public void removeCity(Favorite favorite) {
        city = favorite.getCity();
        state = favorite.getState();
        favoriteList.remove(favorite);
        favoriteListAdapter.remove(favorite);
        String json = gson.toJson(favoriteList);
        prefsEditor.clear();
        prefsEditor.putString("favorites", json);
        prefsEditor.commit();
        Toast.makeText(this, "City Deleted", Toast.LENGTH_SHORT).show();
        if (favoriteList.size() == 0) {
            msg.setVisibility(View.VISIBLE);
            favoriteLabel.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        city = cityTV.getText().toString().trim();
        state = stateTV.getText().toString().trim();
        if (city == null || state == null || "".equalsIgnoreCase(city) || "".equalsIgnoreCase(state)) {
            Toast.makeText(this, "Enter city and state", Toast.LENGTH_LONG).show();
            return;
        }
        showForecast(city, state);
    }

    public void showForecast(String city, String state) {
        Intent intent = new Intent(this, CityWeatherActivity.class);
        intent.putExtra("city", city);
        intent.putExtra("state", state.toUpperCase());
        startActivity(intent);
    }

    @Override
    protected void onPostResume() {
        Log.d(",", "");
        mPrefs = getSharedPreferences("favoritesFile", MODE_PRIVATE);
        prefsEditor = mPrefs.edit();
        gson = new Gson();
        String json = mPrefs.getString("favorites", "");
        json = mPrefs.getString("favorites", "");
        Type type = new TypeToken<List<Favorite>>() {
        }.getType();
        favoriteList = (ArrayList<Favorite>) gson.fromJson(json, type);
        favoriteLabel = (TextView) findViewById(R.id.favoriteLabel);

        if (favoriteList != null && favoriteList.size() > 0) {
            msg.setVisibility(View.GONE);
            favoriteListAdapter = new FavoriteListAdapter(this, R.layout.favoritelist, favoriteList);
            favoriteListAdapter.setNotifyOnChange(true);
            favorites.setAdapter(favoriteListAdapter);

            favoriteLabel.setVisibility(View.VISIBLE);
        } else {
            msg.setVisibility(View.VISIBLE);
            favoriteLabel.setVisibility(View.GONE);
        }
        favorites.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Favorite favorite = favoriteList.get(position);
                city = favorite.getCity();
                state = favorite.getState();
                showForecast(city, state);
            }
        });
        super.onPostResume();
    }
}

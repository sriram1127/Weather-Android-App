package com.example.sriram.weatherapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sriram.hw5_weatherapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by SRIRAM on 08-10-2016.
 */
public class WeatherAdapter extends ArrayAdapter<Weather> {

    private Context context;
    private int resource;
    private List<Weather> hourlyWeather;

    public WeatherAdapter(Context context, int resource, List<Weather> hourlyWeather) {
        super(context, resource, hourlyWeather);
        this.context = context;
        this.resource = resource;
        this.hourlyWeather = hourlyWeather;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(resource, parent, false);
        }
        Weather weather = hourlyWeather.get(position);
        ImageView cloudImage = (ImageView) convertView.findViewById(R.id.cloudImage);
        Picasso.with(context).load(weather.getIconUrl()).into(cloudImage);
        TextView timeTV = (TextView) convertView.findViewById(R.id.timeTv);
        timeTV.setText(weather.getTime());
        TextView cloudTV = (TextView) convertView.findViewById(R.id.cloud);
        cloudTV.setText(weather.getCloud());
        TextView tempTV = (TextView) convertView.findViewById(R.id.temp);
        tempTV.setText(weather.getTemperature());
        return convertView;
    }
}

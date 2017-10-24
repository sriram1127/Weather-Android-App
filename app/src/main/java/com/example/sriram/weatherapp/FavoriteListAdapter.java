package com.example.sriram.weatherapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.sriram.hw5_weatherapp.R;

import java.util.List;

/**
 * Created by SRIRAM on 10-10-2016.
 */
public class FavoriteListAdapter extends ArrayAdapter<Favorite> {

    private String dateLabel = "Updated on";
    Context context = null;
    int resource;
    List<Favorite> weatherForecasts;

    public FavoriteListAdapter(Context context, int resource, List<Favorite> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.weatherForecasts = objects;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Favorite favorite = weatherForecasts.get(position);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(resource, parent, false);
        }
        TextView location = (TextView) convertView.findViewById(R.id.location);
        String cityAndState = favorite.getCity() + ", " + favorite.getState();
        location.setText(cityAndState);

        TextView temp = (TextView) convertView.findViewById(R.id.tempInF);
        temp.setText(favorite.getTemperature());

        TextView date = (TextView) convertView.findViewById(R.id.storedDate);
        date.setText(dateLabel +favorite.getUpdatedDate());

        return convertView;
    }
}

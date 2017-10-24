package com.example.sriram.weatherapp;

import java.io.Serializable;

/**
 * Created by SRIRAM on 08-10-2016.
 */
public class Weather implements Serializable {

    private String time;

    private String temperature;

    private String dewPoint;

    private String cloud;

    private String iconUrl;

    private String windSpeed;

    private String windDirection;

    private String climateType;

    public Weather() {
    }


    private String humidity;

    private String feelsLike;

    private String maximumTemp;

    private String minimumTemp;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getDewPoint() {
        return dewPoint;
    }

    public void setDewPoint(String dewPoint) {
        this.dewPoint = dewPoint;
    }

    public String getCloud() {
        return cloud;
    }

    public void setCloud(String cloud) {
        this.cloud = cloud;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    public String getClimateType() {
        return climateType;
    }

    public void setClimateType(String climateType) {
        this.climateType = climateType;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getFeelsLike() {
        return feelsLike;
    }

    public void setFeelsLike(String feelsLike) {
        this.feelsLike = feelsLike;
    }

    public String getMaximumTemp() {
        return maximumTemp;
    }

    public void setMaximumTemp(String maximumTemp) {
        this.maximumTemp = maximumTemp;
    }

    public String getMinimumTemp() {
        return minimumTemp;
    }

    public void setMinimumTemp(String minimumTemp) {
        this.minimumTemp = minimumTemp;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    private String pressure;




}

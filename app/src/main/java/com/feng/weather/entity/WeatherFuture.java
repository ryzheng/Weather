package com.feng.weather.entity;

/**
 * author 丰
 * date   2018/12/6
 * desc
 */
public class WeatherFuture {
    private String temperature;
    private String weather;
    private String wind;
    private String week;
    private String date;
    private WeatherId weather_id;

    public WeatherFuture() {
    }

    public WeatherFuture(String temperature, String weather, String wind, String week, String date, WeatherId weather_id) {
        this.temperature = temperature;
        this.weather = weather;
        this.wind = wind;
        this.week = week;
        this.date = date;
        this.weather_id = weather_id;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public WeatherId getWeather_id() {
        return weather_id;
    }

    public void setWeather_id(WeatherId weather_id) {
        this.weather_id = weather_id;
    }
}

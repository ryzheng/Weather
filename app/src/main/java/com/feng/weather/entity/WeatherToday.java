package com.feng.weather.entity;

/**
 * author 丰
 * date   2018/12/6
 * desc
 */
public class WeatherToday {
    private String city;
    private String date;
    private String temperature;
    private String climate;
    private String wind;
    private String week;
    private WeatherId weatherId;

    public WeatherToday() {
    }

    public WeatherToday(String city, String date, String temperature, String climate, String wind, String week) {
        this.city = city;
        this.date = date;
        this.temperature = temperature;
        this.climate = climate;
        this.wind = wind;
        this.week = week;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getClimate() {
        return climate;
    }

    public void setClimate(String climate) {
        this.climate = climate;
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

    public WeatherId getWeatherId() {
        return weatherId;
    }

    public void setWeatherId(WeatherId weatherId) {
        this.weatherId = weatherId;
    }

    @Override
    public String toString() {
        return "WeatherToday{" +
                "city='" + city + '\'' +
                ", date='" + date + '\'' +
                ", temperature='" + temperature + '\'' +
                ", climate='" + climate + '\'' +
                ", wind='" + wind + '\'' +
                ", week='" + week + '\'' +
                '}';
    }
}

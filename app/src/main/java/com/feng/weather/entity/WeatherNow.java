package com.feng.weather.entity;

/**
 * author 丰
 * date   2018/12/6
 * desc
 */
public class WeatherNow {
    private String temp;
    private String humidity;
    private String time;
    private WeatherId weatherId;

    public WeatherNow() {
    }

    public WeatherNow(String temp, String humidity, String time) {
        this.temp = temp;
        this.humidity = humidity;
        this.time = time;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "WeatherNow{" +
                "temp='" + temp + '\'' +
                ", humidity='" + humidity + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}

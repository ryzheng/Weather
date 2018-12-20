package com.feng.weather.utils;

import com.feng.weather.entity.WeatherFuture;
import com.feng.weather.entity.WeatherId;
import com.feng.weather.entity.WeatherNow;
import com.feng.weather.entity.WeatherToday;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * author 丰
 * date   2018/12/10
 * desc
 */
public class JsonParse {

    public static WeatherNow parseWeatherNow(JSONObject obj) throws JSONException {
        WeatherNow weatherNow = new WeatherNow();
        weatherNow.setHumidity("湿度：" + obj.getString("humidity"));
        weatherNow.setTemp("温度：" + obj.getString("temp") + "℃");
        weatherNow.setTime("更新时间：" + obj.getString("time"));
        return weatherNow;
    }

    public static WeatherToday parseWeatherToday(JSONObject obj) throws JSONException {
        WeatherToday weatherToday = new WeatherToday();
        weatherToday.setCity(obj.getString("city") + "市");
        weatherToday.setClimate(obj.getString("weather"));
        weatherToday.setDate(obj.getString("date_y"));
        weatherToday.setTemperature(obj.getString("temperature"));
        weatherToday.setWeek(obj.getString("week"));
        weatherToday.setWind(obj.getString("wind"));
        weatherToday.setWeatherId(parseWeatherId(obj.getJSONObject("weather_id")));
        return weatherToday;
    }

    public static WeatherId parseWeatherId(JSONObject obj) throws JSONException {
        WeatherId weatherId = new WeatherId();
        weatherId.setFa(obj.getString("fa"));
        weatherId.setFb(obj.getString("fb"));
        return weatherId;
    }

    public static WeatherFuture parseWeatherFuture(JSONObject obj) throws JSONException {
        WeatherFuture future = new WeatherFuture();
        future.setWeek(obj.getString("week"));
        future.setDate(obj.getString("date"));
        future.setTemperature(obj.getString("temperature"));
        future.setWeather(obj.getString("weather"));
        future.setWind(obj.getString("wind"));
        future.setWeather_id(parseWeatherId(obj.getJSONObject("weather_id")));
        return future;
    }
}

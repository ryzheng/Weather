package com.feng.weather.utils;

import java.net.URLEncoder;

/**
 * author 丰
 * date   2018/12/10
 * desc
 */
public class UrlFactory {
    public static String getWeatherUrl(String cityName)throws Exception{
        return HttpUtil.WEATHER_PATH+URLEncoder.encode(cityName,"utf-8");
    }
}

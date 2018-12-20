package com.feng.weather.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * author 丰
 * date   2018/12/10
 * desc
 */
public class HttpUtil {

    public static final String WEATHER_PATH="http://v.juhe.cn/weather/index?format=2&key=f7c36c0283d39546567cf0f264eec7ee&cityname=";

    public static InputStream getInputStream(String path) throws IOException {
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        return conn.getInputStream();
    }

    public static String getResponse(InputStream inputStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line = null;
        StringBuilder sb = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }
}

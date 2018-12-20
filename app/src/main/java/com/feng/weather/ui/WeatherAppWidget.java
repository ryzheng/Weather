package com.feng.weather.ui;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.feng.weather.MainActivity;
import com.feng.weather.service.WeatherService;

/**
 * Implementation of App Widget functionality.
 */
public class WeatherAppWidget extends AppWidgetProvider {

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        //监听广播
        Log.d("WeatherAppWidget", "" + intent.getAction());
        Intent service = new Intent(context, WeatherService.class);
        service.putExtra("cityName", MainActivity.getCityName());
        context.startService(service);
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
        Intent service = new Intent(context, WeatherService.class);
        service.putExtra("cityName", MainActivity.getCityName());
        context.startService(service);
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}


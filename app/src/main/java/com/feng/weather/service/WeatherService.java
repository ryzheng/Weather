package com.feng.weather.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;
import android.widget.RemoteViews;

import com.feng.weather.MainActivity;
import com.feng.weather.R;
import com.feng.weather.entity.WeatherNow;
import com.feng.weather.entity.WeatherToday;
import com.feng.weather.ui.WeatherAppWidget;
import com.feng.weather.utils.CityLocate;
import com.feng.weather.utils.HttpUtil;
import com.feng.weather.utils.JsonParse;
import com.feng.weather.utils.UrlFactory;
import com.feng.weather.utils.WeatherUtil;

import org.json.JSONObject;

import java.io.InputStream;

/**
 * <p>author 丰</p>
 * <p>date   2018/12/11</p>
 * <p>desc   后台天气更新服务</p>
 */
public class WeatherService extends Service {
    private static final String TAG="WeatherService";

    private CityLocate locate;

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind");
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
        locate = new CityLocate(getApplicationContext());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
        locate.stopLocate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");
        //开启子线程查询天气数据
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                getWeather(MainActivity.getCityName());
            }
        }).start();
        return super.onStartCommand(intent, flags, startId);
    }

    private void getWeather(String cityName) {
        try {
            //获取输入流
            InputStream inputStream = HttpUtil.getInputStream(UrlFactory.getWeatherUrl(cityName));
            String data = HttpUtil.getResponse(inputStream);

            //JSON解析
            JSONObject jsonResult = new JSONObject(data).getJSONObject("result");
            Log.d("JSON-WeatherService", jsonResult.toString());
            JSONObject skObj = jsonResult.getJSONObject("sk");
            JSONObject todayObj = jsonResult.getJSONObject("today");

            //当前时间的天气
            WeatherNow now = JsonParse.parseWeatherNow(skObj);
            CharSequence updateTime = now.getTime();

            //今天的天气
            WeatherToday today = JsonParse.parseWeatherToday(todayObj);
            CharSequence week = today.getWeek();
            CharSequence temperature = today.getTemperature();
            CharSequence weather = today.getClimate();

            //发送广播
            Intent intent = new Intent();
            intent.setAction("UPDATE_WEATHER_DATA_COMPLETE");
            intent.putExtra("data", data);
            sendBroadcast(intent);
            Log.d("intent", ""+intent.getAction());

            //设置桌面工具RemoteViews数据
            RemoteViews views = new RemoteViews(getPackageName(), R.layout.weather_app_widget);
            views.setImageViewResource(R.id.iv_widget_ico, WeatherUtil.getImage(today.getWeatherId()));
            views.setTextViewText(R.id.tv_widget_week, week);
            views.setTextViewText(R.id.tv_widget_temperature, temperature);
            views.setTextViewText(R.id.tv_widget_weather, weather);
            views.setTextViewText(R.id.tv_update_time, updateTime);
            views.setTextViewText(R.id.tv_widget_location,MainActivity.getCityName());

            //开始定位
            locate.startLocate();

            //按钮响应事件
            Intent service = new Intent(this, WeatherService.class);
            service.putExtra("cityName", MainActivity.getCityName());
            PendingIntent pendingIntent = PendingIntent.getService(
                    this, 0, service, PendingIntent.FLAG_UPDATE_CURRENT);
            views.setOnClickPendingIntent(R.id.ib_widget_update, pendingIntent);
            views.setOnClickPendingIntent(R.id.ib_widget_location_update, pendingIntent);

            //提交更新
            ComponentName componentName =
                    new ComponentName(WeatherService.this, WeatherAppWidget.class);
            AppWidgetManager awm = AppWidgetManager.getInstance(getApplicationContext());
            awm.updateAppWidget(componentName, views);

            //定时任务
            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                    1000 * 60 * 60 + SystemClock.elapsedRealtime(),
                    1000 * 60 * 60, pendingIntent);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

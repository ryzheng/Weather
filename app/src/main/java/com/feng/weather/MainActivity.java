package com.feng.weather;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.feng.weather.entity.WeatherFuture;
import com.feng.weather.entity.WeatherNow;
import com.feng.weather.entity.WeatherToday;
import com.feng.weather.service.WeatherService;
import com.feng.weather.ui.FutureAdapter;
import com.feng.weather.ui.SetCity;
import com.feng.weather.utils.JsonParse;
import com.feng.weather.utils.NetUtil;
import com.feng.weather.utils.ToastUtil;
import com.feng.weather.utils.WeatherUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int RESULT_CITY = 4;

    private List<WeatherFuture> weatherFutures = new ArrayList<>();

    private static String cityName = "绍兴";

    //标题栏
    private ImageView ivUpdate;
    private ImageView ivLocate;
    private TextView tvTitle;
    private ImageView ivSetCity;

    //信息栏
    private TextView tvCityName;
    private TextView tvTime;
    private TextView tvHumidity;
    private TextView tvTempInfo;

    //天气栏
    private TextView tvWeek;
    private TextView tvTemperature;
    private TextView tvClimate;
    private TextView tvWind;
    private ImageView ivWeather;

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefresh;

    private Intent intent;
    private Intent service;
    private InnerBroadcastReceiver receiver;

    private SwipeRefreshLayout.OnRefreshListener refreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            update();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTitle = findViewById(R.id.tv_title_city_name);
        ivUpdate = findViewById(R.id.iv_title_update);
        ivLocate = findViewById(R.id.iv_title_location);
        ivSetCity = findViewById(R.id.iv_city_manager);

        tvCityName = findViewById(R.id.tv_city_name);
        tvTime = findViewById(R.id.tv_time);
        tvHumidity = findViewById(R.id.tv_humidity);
        tvTempInfo = findViewById(R.id.tv_temp_info);

        tvWeek = findViewById(R.id.tv_week);
        tvClimate = findViewById(R.id.tv_climate);
        tvWind = findViewById(R.id.tv_wind);
        tvTemperature = findViewById(R.id.tv_temperature);
        ivWeather = findViewById(R.id.iv_weather);

        recyclerView = findViewById(R.id.rv_future);
        swipeRefresh = findViewById(R.id.srl_refresh);

        //设置按钮监听事件
        ivUpdate.setOnClickListener(this);
        ivSetCity.setOnClickListener(this);
        ivLocate.setOnClickListener(this);
        //设置下拉刷新事件
        swipeRefresh.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light);
        swipeRefresh.setOnRefreshListener(refreshListener);

        intent = new Intent(this, SetCity.class);
        service = new Intent(this, WeatherService.class);

        //设置广播接收器
        receiver = new InnerBroadcastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("UPDATE_WEATHER_DATA_COMPLETE");
        registerReceiver(receiver, filter);

        //百度定位
        setPermission();
    }

    private void setPermission() {
        List<String> permissionList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!permissionList.isEmpty()) {
            String[] permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(this, permissions, 1);
        } else {
            update();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    for (int result : grantResults) {
                        if (result != PackageManager.PERMISSION_GRANTED) {
                            ToastUtil.showToast(this, "必须同意权限才能使用该功能");
                            setPermission();
                            return;
                        }
                    }
                } else {
                    ToastUtil.showToast(this, "发生未知错误");
                    setPermission();
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //注销服务和广播接收器
        if (service != null) {
            stopService(service);
        }
        if (receiver != null) {
            unregisterReceiver(receiver);
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.iv_city_manager:
                startActivityForResult(intent, RESULT_CITY);
                break;
            case R.id.iv_title_update:
                update();
                break;
            case R.id.iv_title_location:
                startService(service);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    //接收返回的城市名称
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        cityName = data != null ? data.getStringExtra("cityName") : "绍兴";
        Log.d("CityName", cityName);
        update();
    }

    private void update() {
        //网络判断
        int netStatus = NetUtil.getNetStatus(this);
        switch (netStatus) {
            case NetUtil.NETWORK_NULL:
                ToastUtil.showToast(this, "网络错误");
                break;
            case NetUtil.NETWORK_PHONE:
                ToastUtil.showToast(this, "正在使用数据网络");
            case NetUtil.NETWORK_WIFI:
                startService(service);
                break;
            default:
                ToastUtil.showToast(this, "网络错误");
        }
    }

    //使用广播接收数据
    class InnerBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            //JSON解析
            try {
                JSONObject jsonResult = new JSONObject(intent.getStringExtra("data"))
                        .getJSONObject("result");
                Log.d("JSON-MainActivity", jsonResult.toString());
                JSONArray futureArr = jsonResult.getJSONArray("future");
                JSONObject skObj = jsonResult.getJSONObject("sk");
                JSONObject todayObj = jsonResult.getJSONObject("today");

                //当前时间的天气
                WeatherNow now = JsonParse.parseWeatherNow(skObj);
                tvHumidity.setText(now.getHumidity());
                tvTime.setText(now.getTime());
                tvTempInfo.setText(now.getTemp());

                //今天的天气
                WeatherToday today = JsonParse.parseWeatherToday(todayObj);
                tvTemperature.setText(today.getTemperature());
                tvClimate.setText(today.getClimate());
                tvCityName.setText(today.getCity());
                tvTitle.setText(today.getCity());
                tvWeek.setText(today.getWeek());
                tvWind.setText(today.getWind());
                ivWeather.setImageResource(WeatherUtil.getImage(today.getWeatherId()));

                //未来七天的天气
                weatherFutures.clear();
                for (int i = 1; i < futureArr.length(); i++) {
                    JSONObject futureObj = futureArr.getJSONObject(i);
                    weatherFutures.add(JsonParse.parseWeatherFuture(futureObj));
                }
                setFuture();

                //停止刷新动画
                swipeRefresh.setRefreshing(false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //通过RecyclerView布局显示未来七天的天气状况
    private void setFuture() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        //设置列表方向为横向
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        FutureAdapter adapter = new FutureAdapter(weatherFutures);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }

    public static String getCityName() {
        return cityName;
    }

    public static void setCityName(String cityName) {
        MainActivity.cityName = cityName;
    }
}

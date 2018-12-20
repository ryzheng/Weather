package com.feng.weather.utils;

import android.content.Context;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.feng.weather.MainActivity;

/**
 * <p>author 丰</p>
 * <p>date   2018/12/20</p>
 * <p>desc   百度定位获取地址</p>
 */
public class CityLocate extends BDAbstractLocationListener {

    private Context mContext;
    private LocationClient locationClient;

    public CityLocate(Context context) {
        mContext = context;

        //使用百度定位
        locationClient = new LocationClient(mContext);
        locationClient.registerLocationListener(this);

        //设置定位选项
        LocationClientOption option = new LocationClientOption();
        option.setIsNeedAddress(true);
        locationClient.setLocOption(option);
    }

    @Override
    public void onReceiveLocation(BDLocation location) {
        //获取城市
        String city = location.getCity();
        ToastUtil.showToast(mContext, "当前定位-" + city);
        MainActivity.setCityName(city);
    }

    //开启定位
    public void startLocate() {
        if (locationClient != null) {
            if (locationClient.isStarted()) {
                locationClient.restart();
            }else {
                locationClient.start();
            }
        }
    }

    //关闭定位
    public void stopLocate() {
        if (locationClient != null) {
            locationClient.stop();
        }
    }
}

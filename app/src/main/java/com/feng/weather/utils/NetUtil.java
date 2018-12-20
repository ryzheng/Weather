package com.feng.weather.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * author 丰
 * date   2018/12/4
 * desc   判断网络状态
 */
public class NetUtil {
    public static final int NETWORK_NULL = 0;
    public static final int NETWORK_PHONE = 1;
    public static final int NETWORK_WIFI = 2;

    public static int getNetStatus(Context context) {
        ConnectivityManager connManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connManager.getActiveNetworkInfo();
        if(networkInfo==null){
            return NETWORK_NULL;
        }
        int networkType=networkInfo.getType();

        if(networkType==ConnectivityManager.TYPE_MOBILE){
            return NETWORK_PHONE;
        }else if(networkType==ConnectivityManager.TYPE_WIFI){
            return NETWORK_WIFI;
        }else {
            return NETWORK_NULL;
        }
    }
}

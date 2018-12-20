package com.feng.weather.utils;

import com.feng.weather.R;
import com.feng.weather.entity.WeatherId;

/**
 * <p>author 丰</p>
 * <p>date   2018/12/10</p>
 * <p>desc   通过weatherId获取对应的图片</p>
 */
public class WeatherUtil {

    /**
     * 通过weatherId获取对应的图片
     *
     * @param id weatherId
     * @return 图片R资源地址
     */
    public static int getImage(WeatherId id) {
        switch (id.getFa()) {
            case "00":
                return R.drawable.weather_qing;
            case "01":
                return R.drawable.weather_duoyun;

            case "02":
                return R.drawable.weather_yin;

            case "03":
                return R.drawable.weather_zhenyu;

            case "04":
                return R.drawable.weather_leizhenyu;

            case "05":
                return R.drawable.weather_leizhenyubingbao;

            case "06":
                return R.drawable.weather_yujiaxue;

            case "07":
            case "19":
            case "21":
                return R.drawable.weather_xiaoyu;

            case "08":
            case "22":
                return R.drawable.weather_zhongyu;

            case "09":
            case "23":
                return R.drawable.weather_dayu;

            case "10":
            case "24":
                return R.drawable.weather_baoyu;

            case "11":
            case "25":
                return R.drawable.weather_dabaoyu;

            case "12":
                return R.drawable.weather_tedabaoyu;

            case "13":
                return R.drawable.weather_zhenxue;

            case "14":
            case "26":
                return R.drawable.weather_xiaoxue;

            case "15":
            case "27":
                return R.drawable.weather_zhongxue;

            case "16":
            case "28":
                return R.drawable.weather_daxue;

            case "17":
                return R.drawable.weather_baoxue;

            case "18":
                return R.drawable.weather_wu;

            case "20":
            case "29":
            case "30":
            case "31":
                return R.drawable.weather_shachenbao;

            case "53":
                return R.drawable.weather_wu;
        }
        return R.drawable.weather_qing;
    }
}

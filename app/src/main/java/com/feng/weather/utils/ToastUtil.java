package com.feng.weather.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * author 丰
 * date   2018/12/4
 * desc
 */
public class ToastUtil {
    private static Toast toast;

    public static void showToast(Context context, String msg) {
        if (toast == null) {
            toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        } else {
            toast.setText(msg);
        }
        toast.show();
    }
}

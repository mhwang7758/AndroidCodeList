package com.mhwang.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.util.DisplayMetrics;

/**
 * Description :
 * Author :mhwang
 * Date : 2018/1/4
 * Version : V1.0
 */

public class ScreenUtil {

    /** 判断屏幕是否横屏
     * @param context
     * @return true, 横屏,false, 竖屏
     */
    public static boolean isLand(Context context){
        Configuration mConfiguration = context.getResources().getConfiguration(); //获取设置的配置信息
        return mConfiguration.orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    /** 获取屏幕参数
     * @param context
     * @return
     */
    public static DisplayMetrics getScreenMetrics(Context context){
        return context.getApplicationContext().getResources().getDisplayMetrics();
    }
}

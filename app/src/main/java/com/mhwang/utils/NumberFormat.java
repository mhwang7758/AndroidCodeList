package com.mhwang.utils;

import android.text.TextUtils;

import java.util.regex.Pattern;

/**
 * Description :
 * Author :mhwang
 * Date : 2017/10/30
 * Version : V1.0
 */

public class NumberFormat {
    public static String getPriceFormat(double price){
        // 转换为String类型
        String sPrice = Double.toString(price);
        String[] prices = sPrice.split("\\.");
        String formatPrice;
        // 处理小数点后的数据，保留2位小数
        if (prices.length >= 2) {
            String decimal = prices[1];
            if (!TextUtils.isEmpty(decimal)) {
                if (decimal.length() > 2) {
                    decimal = decimal.substring(0, 2);
                }
            }
            formatPrice = prices[0] + "." + decimal;
        }else{
            formatPrice = prices[0];
        }

        return formatPrice.startsWith(".") ? "0"+formatPrice : formatPrice;
    }

    /** 判断是否数字
     * @param str
     * @return
     */
    public static boolean isNumber(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }
}

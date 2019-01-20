package com.mhwang.utils;


import java.math.BigInteger;

/**
 * Description :  进制转换类
 * Author :mhwang
 * Date : 2017/5/10
 * Version : V1.0
 */

public class BytesUtil {
    /**
     *  普通字符转换成16进制字符串
     * @param str 需要转换的字符串
     * @return 16进制字符串
     */
    public static String str2HexStr(String str)
    {
        byte[] bytes = str.getBytes();
        BigInteger bigInteger = new BigInteger(1, bytes);
        return bigInteger.toString(16);
    }

    /** 16进制的字符串转换成16进制字符串数组
     * @param src 需要转换的16进制
     * @return byte数组
     */
    public static byte[] hexString2Bytes(String src) {
        int len = src.length() / 2;
        byte[] ret = new byte[len];
        byte[] tmp = src.getBytes();
        for (int i = 0; i < len; i++) {
            ret[i] = uniteBytes(tmp[i * 2], tmp[i * 2 + 1]);
        }
        return ret;
    }

    /** 字节数组转换成16进制字符串显示
     * @param b 需要转换的字节数组
     * @param length 数组长度
     * @return 16进制字符串
     */
    public static String bytes2HexString(byte[] b, int length) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = "0" + hex;
            }
            builder.append(hex.toUpperCase());
        }

        return builder.toString();
    }


    /** int转换成16进制字符串显示
     * @param i 需要转换的数字
     * @return 16进制字符串
     */
    public static String int2HexString(int i) {
        String r = "";
        String hex = Integer.toHexString(i & 0xFF);
        if (hex.length() == 1) {
            hex = "0" + hex;
        }
        r += hex.toUpperCase();

        return r;
    }

    /** 16进制字符串转换10进制
     * @param str 需要转换的16进制字符串
     * @return 10进制数字
     */
    public static int hexString2Int(String str){
        return Integer.parseInt(str,16);
    }

    /** 获取16进制字符串每一位值
     * @param hexValue 需要获取byte的16进制字符串
     * @return byte[]数组
     */
    public static byte[] getEachByteValues(String hexValue){
        int hexLen = hexValue.length()/2;       // 2位16进制代表一个数
        final int byteLength = 8;
        if (hexLen == 0){
            return null;
        }

        byte[] result = new byte[hexLen * byteLength];   // 用来存放byte结果
        String[] hexes = new String[hexLen];             // 存放每个16进制字符串
        int valueIndex = 0;
        // 通过对字符串进行俩俩分割获得每个16进制字符串
        for(int i = 0; i < hexLen; i++){
            hexes[i] = hexValue.substring(valueIndex, valueIndex+2);
            valueIndex += 2;
        }
        int resultIndex = 0;
        // 将每个字符串字节复制到结果数组
        for(String hex : hexes){
            byte[] bytes = getEachByteValue(hex);
            if (bytes == null || bytes.length == 0){
                return null;
            }
            System.arraycopy(bytes, 0, result, resultIndex, byteLength);
            resultIndex += byteLength;
        }
        return result;
    }

    /** 获取16进制值的每一位值
     * @param hexValue (必须保证为2个字节长度的String，否则得不到值)
     * @return 返回长度为8的字节数组，每个值代表每一位的值
     */
    private static byte[] getEachByteValue(String hexValue) {
        if (hexValue.length() != 2){
            return null;
        }
        byte bValue = (byte) Integer.parseInt(hexValue,16);
        byte[] array = new byte[8];
        for(int i = 7; i >= 0; i--){
            array[i] = (byte)(bValue & 1);
            bValue = (byte)(bValue >> 1);
        }
        return array;
    }

    private static byte uniteBytes(byte src0, byte src1) {
        byte _b0 = Byte.decode("0x" + new String(new byte[]{src0})).byteValue();
        _b0 = (byte) (_b0 << 4);
        byte _b1 = Byte.decode("0x" + new String(new byte[]{src1})).byteValue();
        byte ret = (byte) (_b0 ^ _b1);
        return ret;
    }

    /** 10进制数字转byte数组（低位在前，高位在后）
     * @param num 要转换的数字
     * @return byte数组
     */
    public static byte[] int2bytesLFirst(int num) {
        byte[] result = new byte[4];
        result[3] = (byte) ((num >>> 24) & 0xff);
        result[2] = (byte) ((num >>> 16) & 0xff);
        result[1] = (byte) ((num >>> 8) & 0xff);
        result[0] = (byte) (num  & 0xff);
        return result;
    }

    /** 10进制数字转byte数组（高位在前，低位在后）
     * @param num 要转换的数字
     * @return byte数组
     */
    public static byte[] int2bytesHFirst(int num) {
        byte[] result = new byte[4];
        result[0] = (byte) ((num >>> 24) & 0xff);
        result[1] = (byte) ((num >>> 16) & 0xff);
        result[2] = (byte) ((num >>> 8) & 0xff);
        result[3] = (byte) (num & 0xff);
        return result;
    }

    /** bytes转int数字（高位在前，低位在后）
     * @param bytes 要转换的byte数组
     * @return 10进制结果
     */
    public static int bytes2intHFirst(byte[] bytes) {
        int result = 0;
        if (bytes.length == 4) {
            int a = (bytes[0] & 0xff) << 24;
            int b = (bytes[1] & 0xff) << 16;
            int c = (bytes[2] & 0xff) << 8;
            int d = (bytes[3] & 0xff);
            result = a | b | c | d;
        }
        return result;
    }

    /** bytes转int数字（低位在前，高位在后）
     * @param bytes 要转换的bytes
     * @return 10进制结果
     */
    public static int bytes2intLFirst(byte[] bytes) {
        int result = 0;
        if (bytes.length == 4) {
            int a = (bytes[3] & 0xff) << 24;
            int b = (bytes[2] & 0xff) << 16;
            int c = (bytes[1] & 0xff) << 8;
            int d = (bytes[0] & 0xff);
            result = a | b | c | d;
        }
        return result;
    }


}

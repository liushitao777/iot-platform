package com.cpiinfo.iot.commons.utils;

/**
 * @ClassName LonLatUtil
 * @Deacription TODO 经纬度工具类
 * @Author liwenjie
 * @Date 2020/7/31 17:09
 * @Version 1.0
 **/
public class LonLatUtil {
    /**
     * 西面最大值
     */
    //private static double chinaWest = 104.73111147876698;
    /**
     * 东面最大值
     */
    private static double chinaEast = 104.73418007255191;
    /**
     * 南面最大值
     */
    private static double chinaSouth = 25.04321220867503;
    /**
     * 北面最大值
     */
    //private static double chinaNorth = 26.04492428725698;

    public static String randomLonLat(boolean isLongitude) {
        Double d = null;
        if (isLongitude) {
//            BigDecimal bigDecimal = new BigDecimal(Math.random() * (chinaEast - chinaWest) + chinaWest);
//            return bigDecimal.setScale(10, BigDecimal.ROUND_HALF_UP).toString();
            d = Math.random();
            d = d/1000;
            return  String.valueOf(chinaEast + d);
        } else {
//            BigDecimal bigDecimal = new BigDecimal(Math.random() * (chinaNorth - chinaSouth) + chinaSouth);
//            return bigDecimal.setScale(10, BigDecimal.ROUND_HALF_UP).toString();
            d = Math.random();
            d = d/1000;
            return  String.valueOf(chinaSouth + d);
        }
    }

    public static Double randomLonLatDouble(boolean isLongitude) {
        Double d = null;
        if (isLongitude) {
//            BigDecimal bigDecimal = new BigDecimal(Math.random() * (chinaEast - chinaWest) + chinaWest);
//            return bigDecimal.setScale(10, BigDecimal.ROUND_HALF_UP).toString();
            d = Math.random();
            d = d/1000;
            return  d;
        } else {
//            BigDecimal bigDecimal = new BigDecimal(Math.random() * (chinaNorth - chinaSouth) + chinaSouth);
//            return bigDecimal.setScale(10, BigDecimal.ROUND_HALF_UP).toString();
            d = Math.random();
            d = d/1000;
            return  d;
        }
    }

    public static void main(String[] args) {
        System.out.println("经度:"+randomLonLat(true));
        System.out.println("纬度:"+randomLonLat(false));
    }
}

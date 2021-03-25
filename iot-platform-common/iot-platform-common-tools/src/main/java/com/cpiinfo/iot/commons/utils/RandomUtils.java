package com.cpiinfo.iot.commons.utils;

/**
 * @Author: liwenjie
 * @Date: 2019/09/16 09:38:10
 * @Description: 生成随机数的工具类
 */
public class RandomUtils {
    /**
     * 生成范围内随机数
     * @param min
     * @param max
     * @return
     */
    public static int random(int min,int max){
        return min + (int)(Math.random() * ((max - min) + 1));
    }

    /**
     * 生成6位随机数
     */
    public static int randomCode() {
        return (int) ((Math.random() * 9 + 1) * 10000);
    }

    public static void main(String[] args){
        System.out.println(RandomUtils.randomCode());
    }
}

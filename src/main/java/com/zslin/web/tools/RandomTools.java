package com.zslin.web.tools;

/**
 * 随机工具类
 *
 */
public class RandomTools {

    /** 随机6位数 */
    public static String randomCode() {
        Integer res = (int)(Math.random()*1000000);
        return res+"";
    }
}

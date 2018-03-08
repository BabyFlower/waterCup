package com.zslin.web.service;

/**
 * Created by Administrator on 2017/5/5.
 */
public class PayConfigUtil {
    /*账户基本信息配置*/
    public static String APP_ID = "wx73b78c39451e954d";  //微信公众号
    public static String APP_SECRET = "976321f481ff9b19fbd93f1101a06d95";  //微信公众号对应密钥
    public static String MCH_ID = "1448188402";  //商业号
    public static String API_KEY = "njsbz2abcdefghijklmnopqrstuvwxyz";  //商业支付密钥
    /*相关接口，回调地址*/
    public static String UFDODER_URL="https://api.mch.weixin.qq.com/pay/unifiedorder";//微信统一下单接口地址
    public static String NOTIFY_URL="http://106.14.149.28:8090/notify/back";//支付结果回调地址
    public static String CREATE_IP="106.14.149.28";//发起支付终端IP

    /*微信app*/

}

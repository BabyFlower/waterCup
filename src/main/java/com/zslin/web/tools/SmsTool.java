package com.zslin.web.tools;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Created by lsj on 17-5-20.
 */
@Component
public class SmsTool {
    /**
     * 发送注册时的验证码
     *
     */
    private  String Url="http://sh2.ipyy.com/sms.aspx?action=send";
    public void sendRegisterCode(String phoneNumber, String code) {
        StringBuffer sb = new StringBuffer();
        sb.append("欢迎使用水杯子充值中心！您的验证码是：")
                .append(code+"【水杯子】");
        HttpClient client=new HttpClient();
        new Thread(new Runnable() {
            @Override
            public void run() {
                PostMethod post=new PostMethod(Url);
                post.setRequestHeader("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
                NameValuePair userid=new NameValuePair("userid","1");
                NameValuePair account=new NameValuePair("account","jkwl264");
                NameValuePair password=new NameValuePair("password","rty158");
                NameValuePair mobile=new NameValuePair("mobile",phoneNumber);
                NameValuePair content=new NameValuePair("content",sb.toString());
                NameValuePair sendTime=new NameValuePair("sendTime","");
                NameValuePair extno=new NameValuePair("extno","");
                post.setRequestBody(new NameValuePair[]{userid,account,password,mobile,content,sendTime,extno});
                try {
                    int statu=client.executeMethod(post);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}

package com.zslin.web.controller.admin;

import com.zslin.basic.utils.OrderInfoUtil2_0;
import com.zslin.web.model.Account;
import com.zslin.web.model.Order;
import com.zslin.web.service.IAccountService;
import com.zslin.web.service.IOrderService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/2.
 */
@RestController
@RequestMapping("/zhifubaoapp")
public class ZhifubaoAppController {
    @Autowired
    private IAccountService accountService;
    @Autowired
    private IOrderService orderService;
    public static final String APPID = "2017071307741151";

    /** 支付宝账户登录授权业务：入参pid值 */
    public static final String PID = "2088521468950893";
    /** 支付宝账户登录授权业务：入参target_id值 */
    public static final String TARGET_ID = "597246725@qq.com";

/** 商户私钥，pkcs8格式 */
/** 如下私钥，RSA2_PRIVATE 或者 RSA_PRIVATE 只需要填入一个 */
/** 如果商户两个都设置了，优先使用 RSA2_PRIVATE */
/** RSA2_PRIVATE 可以保证商户交易在更加安全的环境下进行，建议使用 RSA2_PRIVATE */
/** 获取 RSA2_PRIVATE，建议使用支付宝提供的公私钥生成工具生成， */
    /** 工具地址：https://doc.open.alipay.com/docs/doc.htm?treeId=291&articleId=106097&docType=1 */
    public static final String RSA2_PRIVATE="MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCcy4dWxDc7SdZdU7RW79Ytu2tWREkK43ORScH7dGLQXGb3o+PMrx64i10H6KpMPY3IODGr+GgAlZle70a3lsmtfOARGQDVWkrF1AEMHD4O/DkbimldHPOEHEscM8RbaihGXLxKgq3J7zINb7kGJwx6gqbJtyiOd1AByyWMuwElc//L08ySPKyXccQ7Ni+sVAG2N8XkvpCeDkOmFpAgNZCoymp3Spus9eVC7tcb/I5hsq32zfKya1PoPBK3/oDBl+Ik4Qlspw1nLFtEmTFdemoDVCrz+MEtIWyRQrDJ43/k1pDK/z+r1I//5GqENUG4D7259J3T/6NS5lkYSxUZSyHpAgMBAAECggEAdjinlO5hTpy/uqKQSTSQ4u57YhIemM0iqBta3cPFdaEWKgYyjnRDz5WU0Y/GR7Hpo1traGTl1fr2JzR2t5pD+kE7mOinTjbERiExoPXyfM4EKRXVl9VqbuP+ZgdKODr0nwjxF6UscCOgM61XbqImsICuTU2CkNi5RIDtkQy3MorutnjpX0DKpHLplORZYiaxz7xDZErqMz/8i3oDf70BzFOLoiwQyXUDjW/BZWpQvgKIpf0d7B6m+kV944r+Xpls0hUD155zhWX4l4SMMX+TUu7HiYjuddAV/5HQBlvVUuXFQoW0pkzt3GYg4+YXQdgypfge6eaRaVYOpHNCxDe4AQKBgQDxEC6+1F1XaVbFbLpdXKxhUwVo9nfTir4OWn1xh6xYZy5cCYxU4F1S4zZNTGJHpjBf7ZzW6aMHHJn/enGD0pPaWjrSS7vTra5Vj7GiB8pQS4mvMboRVVjfqwjmDKwy5rgNS/uPMp/27pvoLQy3DdcruU4tEuZShfk5ZIB3cEY1aQKBgQCmgqimYAnTVaL6iebVQE8hMtmR7TfGLAOWIC5Jvf1vnvLlUONi4ZQ0UiFXBgMU1yg1b2XzywnJ95ggmfBoDWLcfmPUfAPb7Fv0NXacg6Rd91D+P8LI7JDL5gzzIzGqcEvqqdf1QKz3RE2Rt3kjJOc4WsJ0BJ3ff6nuFtDzZLl4gQKBgFB+0t0uFK0no63xDJo4Y+drzZarFoAxCB3K4bCTWV17tuNM3OtKsF8JOAy/wOGkAOtHcOPiyGmLh3Dj9hvKqUx0bMfkOigJCvnPLcWpTElbr/IsbLF1CmRFvIb1HFsy56qpAKtkgD34l18EDAbkPEGBczVh5j8QNZSQlsVLXGZhAoGAIl7mITHB1XXf5oeAtpRHwTqV3qFw2d9L/jk2VNJ69geBbAR+a3LU/NfXOfdru2mOSzlreh93LdGF/XTJQzFALvOH99yBj2wCry46BaTG0P60IUdYFdF/MheSpL2nCt+TTCUzBtUK3x+iQ2nz/2UmtWNGsFwjmnbnxNXA2/z2rAECgYEAi5fMCuaVZR5X44sgHBbc89qeL8k3+5MEUrf9sAV2/B1PAwSw5N5Qqwpie3znjR6BZ80iqBZemjO91Jv2GNUZSpAERwzuJ5e+N1WHBkkuq5t2aaCN4l6JxqbpH64rjV/kZGjZLKOw5jwOyQnxErhjoZqPea0qVbJMBC366z9P+34=";
    //public static final String RSA2_PRIVATE = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCta1XbQyXtzO5xC0RTfE3t//KGxcxtUm0W5QL7h8Z/0yEsU3gSwMx9WcXq6DdA2538nTvsnHcx5XvCVH/mYlWY7al5NunaY0OEicOdMov3p9hBKCKjDo7Pd8obMTmx0W3Vsm4dXweBHuQm+FgLxwoMX2b0tdoncc7WzQ0gMCyZHjDnV3gk2ruLqza2gt4/O0y2X64T4lECCvoMB6fyor7KXdZSX4MNsZtYho4QYyuAXeiKsgfd646HIQDjQsB54C30R2xhh57xWjLZnt3cPjlBzUyEKFZIVtfPcq0C0oImSrae4G3wwoe/Ceh+oN376/Y0wO6f2r4SV9Y5xjjscAQzAgMBAAECggEAdvAqzlrbZKEs1/M2RVv3xsfH+o8micQCdbVTuleA2KJhGhmVPKdmmII9r8LcQmtdi6f7NWiKh3owyzNRFR1UP7KgjTYQZWu3Huutcd79CDnho653MwYFBF3QC55TR5WvkuTxiMtoRjJ9oAyrtkgoqBu2bRdcK7ludvvjiLQBonFiB5M+D5XOEwXsm+/00TC1YPyo0tGULafV4tM/MoGj0lb3jtQnzqxSab8XfezVw2sF3mQcwneMNqErv9KFgBYoLB0SJ41gdM0Euz8c8vugfVeF9c2j/20l1nR5qs37S4Y0a8US5bu8GhxONpl7mGljmvJ3osYsrrPHlKamuIj/4QKBgQD1i7CtTPLtZf3e9LDd5r1EFuXUjbC6Yqpgx/BcSTqFO++4RO+d+HsPT6mSqUhUjZnMnKM9Y/ngwHhs+Cc4G0Xj6XMHeXBjSdcz7/CBDJtaUwlB6Jr2KHTfDyuMlKXfl6qvYoT/obBJv6WxZdlbkQYOjUcDpsNEWVblzJhJaa/pdwKBgQC0zYIU2HnODSQbWguuR1/bGFhTxlVz6Ed+5GaQoqLaJV30SHdxhF15nXX6ZQHN211VLDffGh8osdP2t76HjqymYxo1LM23QaVAP+8nMYD/RwXRE2j1tEzVk//dQC4DQfSeIbd0WlVTCZ5C+O6XNOY/HKeJBtGhG4hdTHOygWhqJQKBgQC6Yr3K3rUr9RZ/LjgvSJ30WHk/Hgq/gHOaIUrXkpKEXhm6Q0GsJNisp6itdig8XnwXb/MNf51GuHLxLHEAytsDhMpXKxxfkcZ0WzaxjrNCHfPR/tHZ9p24UBibjp5WmhbrKJqSPNbHNF1+yjPyqKPzWdRE9koU0MgAiYwzdbEdQwKBgC/wgbpHP9y7rUQ3VvuJ30e3p8vmvcey4ioqI2vcQ7P1zni0Mj9+Kk7RynNvDtBnhtLLm7DjOznOFPbI95L36J1hj5Rj4dPiaw+jJCVPQfXt/gPW0rC7ctf6mxAuMUUZ2uOG6x7FyPPwi+8UFCcxlo9LaLyzeyftpfWfDNpa57WlAoGARGiX6Y6eFGCrIFep08scc0A7hyQyVdb7090SD8/ynKIJomJvDXElvLW4hEwiGj3UG50ybCSqphRdRA1xio4X8aUupJKyBWaET/KfI8CC7HmNBYv5fZB6scge4Vapd+A4qrOaETg1VozTxHTQhqcl5feDb+OS1MDE8ps9chfJdvI=";
    public static final String RSA_PRIVATE = "";
    private Map data;
    @RequestMapping(value = "/order", method = RequestMethod.POST)
    public JSONObject zhifubaoapp_order(@RequestParam String username, @RequestParam String fee){
        long time=new Date().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //格式化当前日期
        String ordernumber;
        String paymethod;
        paymethod="zhifubaoapp";
        String sdate = sdf.format(time);
        ordernumber=time+"";
        System.out.println(username);
        System.out.println(ordernumber);
        System.out.println(paymethod);
        System.out.println(fee);
        data=new LinkedHashMap();
        if (username.equals("")||ordernumber.equals("")||paymethod.equals("")||fee.equals("")) {
            data.put("state","orderfailed");
            JSONObject result1 = JSONObject.fromObject(data);
            return result1;
        }else{
            Account account=this.accountService.findByEmail(username);
            if (account==null){
                data.put("state","orderfailed");
                JSONObject result1 = JSONObject.fromObject(data);
                return result1;
            }else {
                Order order = new Order();
                order.setAccount(account);
                order.setFee(fee);
                order.setOrdernumber(ordernumber);
                order.setTime(sdate);
                order.setState("OrderSuccess");
                order.setPaymethod(paymethod);
                this.orderService.save(order);
                data.put("state","ordersuccess");
                boolean rsa2 = (RSA2_PRIVATE.length() > 0);
                Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, rsa2,fee,ordernumber);
                String orderParam = OrderInfoUtil2_0.buildOrderParam(params);
                String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
                String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);
                final String orderInfo = orderParam + "&" + sign;
                data.put("orderinfo",orderInfo);
                JSONObject result1 = JSONObject.fromObject(data);
                return result1;
            }
        }
    }
}
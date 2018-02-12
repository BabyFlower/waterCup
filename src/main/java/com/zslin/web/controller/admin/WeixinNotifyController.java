package com.zslin.web.controller.admin;

/*import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;*/
import com.zslin.web.model.Account;
import com.zslin.web.model.Card;
import com.zslin.web.model.Order;
import com.zslin.web.service.*;

import org.hibernate.sql.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Created by Administrator on 2017/5/8.
 */
@Controller
@RequestMapping("/notify")
public class WeixinNotifyController {
    @Autowired
    private IAccountService accountService;
    @Autowired
    private ICardService iCardService;
    @Autowired
    private IOrderService orderService;
    private String key;
    @RequestMapping(value="/back",method= RequestMethod.POST)
    public void weixin_notify(HttpServletRequest request, HttpServletResponse response,Model model) throws Exception{

        //读取参数
        InputStream inputStream ;
        StringBuffer sb = new StringBuffer();
        inputStream = request.getInputStream();
        String s ;
        String uRemain; //用户当前余额
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        while ((s = in.readLine()) != null){
            sb.append(s);
        }
        in.close();
        inputStream.close();

        //解析xml成map
        Map<String, String> m = new HashMap<String, String>();
        m = XMLUtil.doXMLParse(sb.toString());

        //过滤空 设置 TreeMap
        SortedMap<Object,Object> packageParams = new TreeMap<Object,Object>();
        Iterator it = m.keySet().iterator();
        while (it.hasNext()) {
            String parameter = (String) it.next();
            String parameterValue = m.get(parameter);

            String v = "";
            if(null != parameterValue) {
                v = parameterValue.trim();
            }
            packageParams.put(parameter, v);
        }
        String trade_type = (String)packageParams.get("trade_type");
        // 账号信息
        if (trade_type.equals("APP")){
            key= "abcdefghijklmnopqrstuvwxyznjsbz2";
            System.out.println("appkey"+key);
        }else {
            key = PayConfigUtil.API_KEY; // key
            System.out.println("webkey"+key);
        }
        System.out.println(packageParams);
        //判断签名是否正确
        if(PayCommonUtil.isTenpaySign("UTF-8", packageParams,key)) {
            //------------------------------
            //处理业务开始，读取回调信息
            String mch_id = (String)packageParams.get("mch_id");
            String openid = (String)packageParams.get("openid");
            String is_subscribe = (String)packageParams.get("is_subscribe");
            String out_trade_no = (String)packageParams.get("out_trade_no");
            String attach = (String)packageParams.get("attach");         //用户名
            String total_fee = (String)packageParams.get("total_fee");  //金额


            System.out.println("attach"+attach);
            System.out.println("mch_id:"+mch_id);
            System.out.println("openid:"+openid);
            System.out.println("is_subscribe:"+is_subscribe);
            System.out.println("out_trade_no:"+out_trade_no);
            System.out.println("total_fee:"+total_fee);
            //------------------------------
            String resXml = "";
            Order order=this.orderService.findByOrdernumber(out_trade_no);
          if (order!=null) {
              if ("SUCCESS".equals((String) packageParams.get("result_code"))) {
                  // 这里是支付成功
                  //////////执行自己的业务逻辑////////////////
                  //////////修改数据库中的数据////////////////
                  order.setState((String) packageParams.get("result_code"));
                  this.orderService.save(order);
                  Account user = order.getAccount();
                 /* Account user = accountService.findByEmail(attach);*/
                  if (user.getCard().getRest() == null) {
                      uRemain = 0 + "";
                  } else {
                      uRemain = user.getCard().getRest().toString();
                  }
                  System.out.println("remainbeform" + uRemain);
                  float userRemain = Float.parseFloat(uRemain);
                  float userPay = Float.parseFloat(total_fee) / 100;
                  System.out.println("total_fee" + userPay);
                  float remain = userRemain + userPay;
                  String remainfinal = remain + "";
                  Card card = user.getCard();
                  card.setRest(Float.parseFloat(remainfinal));
                  card.setCharge(userPay);
                  card.setPaystate("1");
                  this.iCardService.save(card);//充值到卡

                  System.out.println("remainafter" + user.getCard().getRest());
                  System.out.println("success");

                  //通知微信.异步确认成功.必写.不然会一直通知后台.八次之后就认为交易失败了.
                  resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
                          + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";

              } else {
                  //支付不成功，记录金额与状态
                /*Order order=this.orderService.findByOrdernumber(out_trade_no);*/
                  order.setState((String) packageParams.get("result_code"));
                  this.orderService.save(order);
                  Account user0 = order.getAccount();
                  /*Account user0 = accountService.findByEmail(attach);*/
                  float userPay0 = Float.parseFloat(total_fee) / 100;
                  Card card = user0.getCard();
                  card.setCharge(userPay0);
                  card.setPaystate("0");
               /* accountService.save(user0);*/
                  this.iCardService.save(card);
                  System.out.println("failed,information：" + packageParams.get("err_code"));
                  resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                          + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";

              }
          }else{
              System.out.println("查找订单失败:"+out_trade_no+"订单状态:"+packageParams.get("result_code"));
              resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                      + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
          }
            //------------------------------
            //处理业务完毕
            //------------------------------
            BufferedOutputStream out = new BufferedOutputStream(
                    response.getOutputStream());
            out.write(resXml.getBytes());
            out.flush();
            out.close();
        } else{
            System.out.println("通知签名验证失败");
        }
    }
}

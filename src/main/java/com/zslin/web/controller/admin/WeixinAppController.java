package com.zslin.web.controller.admin;


import com.zslin.web.model.Account;
import com.zslin.web.model.Order;
import com.zslin.web.service.IAccountService;
import com.zslin.web.service.IOrderService;


import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * Created by Administrator on 2017/6/30.
 */
@RestController
@RequestMapping("/weixinapp")
public class WeixinAppController {
    @Autowired
    private IAccountService accountService;
    @Autowired
    private IOrderService orderService;
    private Map data;
    @RequestMapping(value = "/order", method = RequestMethod.POST)
    public JSONObject weixinapp_order(@RequestParam String username, @RequestParam String ordernumber, @RequestParam String paymethod, @RequestParam String fee){
        long time=new Date().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //格式化当前日期
        String sdate = sdf.format(time);
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
                JSONObject result1 = JSONObject.fromObject(data);
                return result1;
            }
      }
    }
}

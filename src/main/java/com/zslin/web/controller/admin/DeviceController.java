package com.zslin.web.controller.admin;

import com.zslin.web.model.Account;
import com.zslin.web.service.IAccountService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Ji on 2017/3/24.
 * 查找用户余额
 */
@RestController
@RequestMapping("/userName")
public class DeviceController {
    @Autowired
    private IAccountService accountService;
    private Map data;
    @RequestMapping(value ="/remain",method= RequestMethod.GET)
    public JSONObject getremain(@RequestParam String username) {
        Account user = this.accountService.findByEmail(username);
        String Paystate;
        String Charge;
        String Cardnumber;
        String state;//标记是否有充值卡
        data = new LinkedHashMap();

        if (user.getCard() == null) {
            state = "0";
            data.put("state", state);
            JSONObject result = JSONObject.fromObject(data);
            return result;
        } else {

        /*有充值卡*/
            //充值状态
            if (user.getCard().getPaystate() == null) {
                Paystate = "2";
            } else {
                Paystate = user.getCard().getPaystate();
            }
            //充值金额
            if (user.getCard().getCharge() == null) {
                Charge = "0";
            } else {
                Charge = user.getCard().getCharge().toString();
            }

            Cardnumber = user.getCard().getCid();
            Long newCardnumber=Long.parseLong(Cardnumber);
            newCardnumber=newCardnumber - 3030303030303030L;
            state = "1";
            data.put("remain", user.getCard().getRest().toString());
            data.put("paystate", Paystate);
            data.put("paymoney", Charge);
            data.put("cardnumber", String.format("%08d", newCardnumber));
            data.put("state", state);
            JSONObject result = JSONObject.fromObject(data);
            //清除状态跟充值金额
            user.getCard().setPaystate("2");
            user.getCard().setCharge((float) 0);
            accountService.save(user);
            return result;
        }
    }
}

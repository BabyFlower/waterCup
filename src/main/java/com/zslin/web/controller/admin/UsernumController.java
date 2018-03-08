package com.zslin.web.controller.admin;

import com.zslin.basic.model.User;
import com.zslin.basic.service.IUserService;
import com.zslin.web.model.Account;
import com.zslin.web.service.IAccountService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.hibernate.procedure.spi.ParameterRegistrationImplementor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * Created by Administrator on 2017/4/13.
 */
@RestController
@RequestMapping("/usernum")
public class UsernumController {
    @Autowired
    private IAccountService accountService;
    @Autowired
    private IUserService userService;
    private Map data;
    @RequestMapping(method= RequestMethod.GET)
public JSONObject getdata(int draw, int start, int length, String user) {
        User usern = this.userService.findByUsername(user);
        if (usern.getIsAdmin()==1) {
            List<Account> test = accountService.findAll();
            data = new LinkedHashMap();
            int len = test.size();
            JSONArray test1 = JSONArray.fromObject(test);
            data.put("draw", draw);
            data.put("recordsTotal", len);
            data.put("recordsFiltered", len);
            if (start + length < len) {
                data.put("data", test1.subList(start, start + length));
            } else {
                data.put("data", test1.subList(start, len));
            }
            JSONObject result1 = JSONObject.fromObject(data);
            return result1;
        }else{
           List<Account> test=new ArrayList<>();
            JSONArray test1 = JSONArray.fromObject(test);
            data = new LinkedHashMap();
            data.put("draw", draw);
            data.put("recordsTotal", 0);
            data.put("recordsFiltered", 0);
            data.put("data",test1.subList(0,0));
            JSONObject result1 = JSONObject.fromObject(data);
            return result1;
        }
    }
}

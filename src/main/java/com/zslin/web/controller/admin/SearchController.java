package com.zslin.web.controller.admin;

import com.sun.org.apache.xml.internal.utils.StringToIntTable;
import com.zslin.basic.model.User;
import com.zslin.basic.service.IUserService;
import com.zslin.web.model.AjaxResponceBody;
import com.zslin.web.model.Device;
import com.zslin.web.model.FirstLevel;
import com.zslin.web.service.IDeviceService;
import com.zslin.web.service.ISendsiteService;
import com.zslin.web.service.ISiteService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.nutz.castor.castor.String2Integer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import com.zslin.web.model.Site;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Array;
import java.util.ArrayList;
import javax.swing.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/30.
 */

@RestController
@RequestMapping("/api")
public class SearchController {
    @Autowired
    private ISendsiteService sendsiteService;
    @Autowired
    private IUserService userService;
    @Autowired
    private ISiteService siteService;
    @Autowired
    private IDeviceService iDeviceService;
    private Map<String,FirstLevel> data;
    private Map<String,FirstLevel> data1;
    @RequestMapping(value = "/sites",method= RequestMethod.GET)
    public JSONObject getAllsites(String id,String user){

         if (id.equals("0")) {
             User usern=this.userService.findByUsername(user);
             if (usern.getIsAdmin()==1){
                 //若为管理员
                 List<Site> list = this.sendsiteService.findAll();
                 data = new HashMap<String, FirstLevel>();
                 for (Site list1 : list) {
                     FirstLevel firstLevel=new FirstLevel();
                     firstLevel.setName(list1.getName());
                     data.put(list1.getId() + "",firstLevel);
                 }
                 JSONObject test = JSONObject.fromObject(data);
                 return test;
             }else {
                 List<Site> list = usern.getSites();
                 data = new HashMap<String, FirstLevel>();

                 for (Site list1 : list) {
                     FirstLevel firstLevel = new FirstLevel();
                     firstLevel.setName(list1.getName());
                     data.put(list1.getId() + "", firstLevel);
                 }
                 JSONObject test = JSONObject.fromObject(data);
                 return test;
             }

         } else {
              Site siteid=this.siteService.findById(Integer.parseInt(id));
//             List<Device> list = iDeviceService.findDeviceBySId(Integer.valueOf(id));
             List<Device> list = siteid.getDevices();
             data1 = new HashMap<String, FirstLevel>();
             for (Device list1 : list) {
                 FirstLevel firstLevel = new FirstLevel();
                 firstLevel.setName(list1.getName());
                 data1.put(list1.getId() + "", firstLevel);
             }
             JSONObject test = JSONObject.fromObject(data1);
             return test;
         }

    }
 }




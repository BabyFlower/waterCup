package com.zslin.web.controller.web;



import com.zslin.web.model.Alarm;
import com.zslin.web.model.Device;
import com.zslin.web.model.Maintain;
import com.zslin.web.model.Senddata;
import com.zslin.web.service.*;


import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Ji on 2017/4/21.
 */

@RestController
@RequestMapping("/device")
public class DatasPController {
    @Autowired
    ISenddatasService iSenddatasService;
    @Autowired
    IDeviceService iDeviceService;
    @Autowired
    IAlarmService iAlarmService;
    @Autowired
    IMaintainService iMaintainService;
    @RequestMapping(value = "/datasp",method = RequestMethod.POST)
    public JSONObject handledatas(@RequestBody JSONObject jobj){
        System.out.println("有请求");
        Alarm alarm=this.iAlarmService.findOne(1);
        String id1=jobj.getString("id");
        Integer id2;
        Integer target=0;
       id2= this.iDeviceService.findBymodel_id(Long.valueOf(id1)).getId();
        System.out.println(id2);
      /*
        if(id1.equals("1")){
            id2="1";
        }else{
            id2="3";
        }*/
        String datas=jobj.getString("data");
        Map<String,String> tmp=new HashMap<>();
        JSONObject jbt=JSONObject.fromObject(datas);
       /* List<String> data=new LinkedList<>();
        data.add(jbt.getString("temp"));
        data.add(jbt.getString("flow1"));
        data.add(jbt.getString("flow2"));
        data.add(jbt.getString("tds1"));
        data.add(jbt.getString("tds2"));*/
       Senddata sd=new Senddata();
       Device device=this.iDeviceService.findOne(Integer.valueOf(id2));
       Maintain maintain=new Maintain();
       sd.setD_id(Integer.valueOf(id2));

        if(!jbt.optString("tds2","").equals("")){
       sd.setCstds(jbt.getString("tds2"));
        if (Float.parseFloat(jbt.getString("tds2"))<alarm.getCstdsdown()
                ||Float.parseFloat(jbt.getString("tds2"))>alarm.getCstdsup()){
           maintain.setCstds(Float.parseFloat(jbt.getString("tds2")));
           target=1;
       }
       }
        if(!jbt.optString("tds1","").equals("")){
       sd.setJstds(jbt.getString("tds1"));
        if (Float.parseFloat(jbt.getString("tds1"))<alarm.getJstdsdown()
                ||Float.parseFloat(jbt.getString("tds1"))>alarm.getJstdsup()){
            maintain.setJstds(Float.parseFloat(jbt.getString("tds1")));
            target=1;
        }}
        if(!jbt.optString("temp","").equals("")){
       sd.setSuwd(jbt.getString("temp"));
        if (Float.parseFloat(jbt.getString("temp"))<alarm.getSuwddown()
                ||Float.parseFloat(jbt.getString("temp"))>alarm.getSuwdup()){
            maintain.setSuwd(Float.parseFloat(jbt.getString("temp")));
            target=1;
        }}
        //把进水流量换成了出水压力，原来的压力作为进水压力


        if(!jbt.optString("press2","").equals("")){
       sd.setJsll(jbt.getString("press2"));}
       /* if (Float.parseFloat(jbt.getString("flow1"))<alarm.getJslldown()
                ||Float.parseFloat(jbt.getString("flow1"))>alarm.getJsllup()){
            maintain.setJsll(Float.parseFloat(jbt.getString("flow1")));
            target=1;
        }*/
        if(!jbt.optString("flow2","").equals("")){
       sd.setCsll(jbt.getString("flow2"));
        if (Float.parseFloat(jbt.getString("flow2"))<alarm.getCslldown()
                ||Float.parseFloat(jbt.getString("flow2"))>alarm.getCsllup()){
            maintain.setCsll(Float.parseFloat(jbt.getString("flow2")));
            target=1;
        }}

        System.out.println(1);

        if(!jbt.optString("ph","").equals("")){
        sd.setPh(jbt.getString("ph"));}
        if(!jbt.optString("cl","").equals("")){
        sd.setYlv(jbt.getString("cl"));}
        if(!jbt.optString("o3","").equals("")){
        sd.setYcy(jbt.getString("o3"));}
        if(!jbt.optString("tur","").equals("")){
        sd.setZd(jbt.getString("tur"));}
        if(!jbt.optString("do","").equals("")){
        sd.setRjy(jbt.getString("do"));}
        if(!jbt.optString("press1","").equals("")){
        sd.setYl(jbt.getString("press1"));}

        System.out.println(11);
      /* sd.setPh("OK");
       sd.setYl("OK");
        sd.setYlv("OK");
        sd.setZd("OK");
        sd.setRjy("OK");
        sd.setYcy("OK");
        sd.setSuyl("OK");*/
        DateFormat format2 = new SimpleDateFormat("yyyyMMddHHmmss");

        format2.setTimeZone(TimeZone.getTimeZone("GMT+8"));

        sd.setSentem( format2.format(new Date()).toString());
        System.out.println(111);

       this.iSenddatasService.save(sd);

       if (target==1){
           System.out.println(11111112);
          /* if (this.iMaintainService.findByDeviceId(id2)!=null){
               System.out.println(5555);
               this.iMaintainService.delete(this.iMaintainService.findByDeviceId(id2).getId());
           }*/
           System.out.println(1112);
           maintain.setPh(null);
           maintain.setYl(null);
           maintain.setYlv(null);
           maintain.setZd(null);
           maintain.setRjy(null);
           maintain.setYcy(null);
           maintain.setSuyl(null);
           System.out.println(1113);
           maintain.setSentem( format2.format(new Date()).toString());
           maintain.setDeviceId(Integer.valueOf(id2));
           maintain.setDevicename(device.getName());
           maintain.setSitename(device.getSite().getName());
           System.out.println(11134);
           this.iMaintainService.save(maintain);
           System.out.println(1115);
       }
        System.out.println(1111);
        //成功
        if(datas.length()!=0){
            tmp.put("msg","Success");
            tmp.put("error_code","0");
            JSONObject response=JSONObject.fromObject(tmp);
            return response;
        }
        //失败
        else{
            tmp.put("msg","Error");
            tmp.put("error_code","1");
            JSONObject response=JSONObject.fromObject(tmp);
            return response;
        }
    }
}

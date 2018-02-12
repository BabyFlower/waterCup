package com.zslin.web.controller.admin;

import com.zslin.web.model.Alarm;
import com.zslin.web.service.IAlarmService;
/*import jdk.internal.org.objectweb.asm.tree.analysis.Value;*/
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2017/6/19.
 */

@Controller

public class AlarmController {
    @Autowired
    private IAlarmService alarmService;
    @RequestMapping(value = "/alarm")
    public String verifyalarm(Model model, HttpServletRequest request){
        String rjyup=request.getParameter("rjyup");
        String rjydown=request.getParameter("rjydown");
        String suwdup=request.getParameter("suwdup");
        String suwddown=request.getParameter("suwddown");
        String suylup=request.getParameter("suylup");
        String suyldown=request.getParameter("suyldown");
        String jsllup=request.getParameter("jsllup");
        String jslldown=request.getParameter("jslldown");
        String csllup=request.getParameter("csllup");
        String cslldown=request.getParameter("cslldown");
        String jstdsup=request.getParameter("jstdsup");
        String jstdsdown=request.getParameter("jstdsdown");
        String cstdsup=request.getParameter("cstdsup");
        String cstdsdown=request.getParameter("cstdsdown");
        String phup=request.getParameter("phup");
        String phdown=request.getParameter("phdown");
        String ylup=request.getParameter("ylup");
        String yldown=request.getParameter("yldown");
        String ylvup=request.getParameter("ylvup");
        String ylvdown=request.getParameter("ylvdown");
        String ycyup=request.getParameter("ycyup");
        String ycydown=request.getParameter("ycydown");
        String zdup=request.getParameter("zdup");
        String zddown=request.getParameter("zddown");
        Alarm alarm=this.alarmService.findOne(1);
        if (!rjyup.equals("")){
            alarm.setRjyup(Float.parseFloat(rjyup));
        }
        if(!rjydown.equals("")){
            alarm.setRjydown(Float.parseFloat(rjydown));
        }
        if(!suwdup.equals("")){
            alarm.setSuwdup(Float.parseFloat(suwdup));
        }
        if (!suwddown.equals("")){
            alarm.setSuwddown(Float.parseFloat(suwddown));
        }
        if(!suylup.equals("")){
            alarm.setSuylup(Float.parseFloat(suylup));
        }
        if(!suyldown.equals("")){
            alarm.setSuyldown(Float.parseFloat(suyldown));
        }
        if(!jsllup.equals("")){
            alarm.setJsllup(Float.parseFloat(jsllup));
        }
        if(!jslldown.equals("")){
            alarm.setJslldown(Float.parseFloat(jslldown));
        }
        if(!csllup.equals("")){
            alarm.setCsllup(Float.parseFloat(csllup));
        }
        if(!cslldown.equals("")){
            alarm.setCslldown(Float.parseFloat(cslldown));
        }
        if(!jstdsup.equals("")){
            alarm.setJstdsup(Float.parseFloat(jstdsup));
        }
        if(!jstdsdown.equals("")){
            alarm.setJstdsdown(Float.parseFloat(jstdsdown));
        }
        if(!cstdsup.equals("")){
            alarm.setCstdsup(Float.parseFloat(cstdsup));
        }
        if(!cstdsdown.equals("")){
            alarm.setCstdsdown(Float.parseFloat(cstdsdown));
        }
        if(!phup.equals("")){
            alarm.setPhup(Float.parseFloat(phup));
        }
        if(!phdown.equals("")){
            alarm.setPhdown(Float.parseFloat(phdown));
        }
        if(!ylup.equals("")){
            alarm.setYlup(Float.parseFloat(ylup));
        }
        if(!yldown.equals("")){
            alarm.setYldown(Float.parseFloat(yldown));
        }
        if(!ylvup.equals("")){
            alarm.setYlvup(Float.parseFloat(ylvup));
        }
        if(!ylvdown.equals("")){
            alarm.setYlvdown(Float.parseFloat(ylvdown));
        }
        if(!ycyup.equals("")){
            alarm.setYcyup(Float.parseFloat(ycyup));
        }
        if(!ycydown.equals("")){
            alarm.setYcydown(Float.parseFloat(ycydown));
        }
        if(!zdup.equals("")){
            alarm.setZdup(Float.parseFloat(zdup));
        }
        if(!zddown.equals("")){
            alarm.setZddown(Float.parseFloat(zddown));
        }
        this.alarmService.save(alarm);
        model.addAttribute("errMg","保存成功");
        model.addAttribute("alarm",alarm);
        return "pages/alarm";
    }
}

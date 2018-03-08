package com.zslin.web.controller.admin;

import com.sun.mail.imap.protocol.ID;
import com.zslin.basic.model.User;
import com.zslin.web.model.Device;
import com.zslin.web.model.Site;
import com.zslin.web.service.IDeviceService;
import com.zslin.web.service.ISiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;


/**
 * Created by Administrator on 2017/6/12.
 */
@Controller
@RequestMapping("/devices")
public class AddDeviceController {
    @Autowired
    private ISiteService siteService;
    @Autowired
    private IDeviceService deviceService;
    @RequestMapping(value = "/add" )
    public String adddevice(Model model,HttpServletRequest request){
        String devicename=request.getParameter("name");
        String sn=request.getParameter("sn");
        String errMg=new String();
     if(sn.equals("")||devicename.equals("")){
         errMg="设备名或设备编号不能为空";
         model.addAttribute("errMg",errMg);
         return "/pages/adddevice";
     }else{
         String siteid=request.getParameter("siteid");
         Site site=this.siteService.findById(Integer.parseInt(siteid));
         Device device=new Device();
         device.setName(devicename);
         device.setModel_id(Long.parseLong(sn));
         device.setSite(site);
         deviceService.save(device);
         errMg=devicename+"设备保存成功";
         model.addAttribute("errMg",errMg);
         return "/pages/adddevice";
     }
    }
}

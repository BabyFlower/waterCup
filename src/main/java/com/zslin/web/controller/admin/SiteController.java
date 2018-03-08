package com.zslin.web.controller.admin;

import com.zslin.basic.model.User;
import com.zslin.basic.service.IUserService;
import com.zslin.web.model.Site;
import com.zslin.web.service.ISiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Ji on 2017/3/24.
 */
@Controller

@RequestMapping("/sites")
public class SiteController {
    @Autowired
    private IUserService userService;
    @Autowired
    private ISiteService siteService;
    @RequestMapping(value = "/add")
    public String addsite(Model model, HttpServletRequest request){
       String username=request.getParameter("username");
       User user=this.userService.findByUsername(username);
       String sitename=request.getParameter("sitename");
       String phonenumber=request.getParameter("phonenumber");
       String sitephone=request.getParameter("sitephone");
       String errMg=new String();
        List<User> users=this.userService.findAll();
        model.addAttribute("prods",users);
       if(sitename.equals("")) {
           errMg="站点名不能为空";
           model.addAttribute("errMg",errMg);
           return "/pages/addsite";
            }else{
           Site site = new Site();
           site.setName(sitename);
           site.setSitephone(sitephone);
           site.setPhonenumber(phonenumber);
           site.setUser(user);
           siteService.save(site);
           errMg=sitename+"站点保存成功";
           model.addAttribute("errMg",errMg);
           return "/pages/addsite";
                }
    }
}

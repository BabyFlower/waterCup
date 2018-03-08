package com.zslin.web.controller.admin;

import com.zslin.basic.model.User;
import com.zslin.basic.service.IUserService;
import com.zslin.basic.tools.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2017/6/13.
 */
@Controller
@RequestMapping("/users")
public class AddUserController {
    @Autowired
    private IUserService userService;
    @RequestMapping(value = "/add")
    public String addusers(Model model,HttpServletRequest request) {
        String username = request.getParameter("username");
        String isadmain = request.getParameter("isadmain");
        String password = request.getParameter("password");
        String errMg = new String();
        Date d = new Date();
        /*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateNowStr = sdf.format(d);*/
            if (this.userService.findByUsername(username) != null) {
                errMg = username + "用户名已存在";
                model.addAttribute("errMg", errMg);
                return "/pages/adduser";
            }else if(password.equals("")||username.equals("")){
                errMg = "用户名或密码不能为空";
                model.addAttribute("errMg", errMg);
                return "/pages/adduser";
            }
            else {
                try {
                    User user = new User();
                    user.setUsername(username);
                    user.setIsAdmin(Integer.parseInt(isadmain));
                    user.setPassword(SecurityUtil.md5(username, password));
                    user.setCreateDate(d);
                    userService.save(user);
                    errMg=username+"用户保存成功";
                    model.addAttribute("errMg", errMg);
                    return "/pages/adduser";
                }catch (Exception e){
                    errMg=username+"用户保存失败";
                    model.addAttribute("errMg", errMg);
                    return "pages/adduser";
                }
            }
        }

    @RequestMapping(value = "/verify")
    public String verifyuser(Model model,HttpServletRequest request){
        String username = request.getParameter("namebefore");
        String newname = request.getParameter("namenew");
        String password = request.getParameter("password");
        String errMg = new String();
        User user=this.userService.findByUsername(username);
        if (user!= null) {
            if(newname.equals("")||password.equals("")){
                errMg = "密码不能为空";
                model.addAttribute("user", user);
                model.addAttribute("errMg", errMg);
                return "/pages/verifyuser";
            }else {
                try {
                    user.setUsername(newname);
                    user.setPassword(SecurityUtil.md5(newname, password));
                    userService.save(user);
                    errMg = username + "用户修改成功";
                    model.addAttribute("errMg", errMg);
                    model.addAttribute("user", user);
                    return "/pages/verifyuser";
                } catch (Exception e) {
                    errMg = username + "用户修改失败";
                    model.addAttribute("user", user);
                    model.addAttribute("errMg", errMg);
                    return "/pages/verifyuser";
                }
            }
        } else {
            errMg = username + "用户名不存在或已失效";
            model.addAttribute("user", user);
            model.addAttribute("errMg", errMg);
            return "/pages/verifyuser";

        }
    }
}

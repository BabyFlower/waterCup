package com.zslin.web.controller.web;

import com.google.zxing.qrcode.decoder.Mode;
import com.zslin.basic.exception.SystemException;
import com.zslin.basic.model.User;
import com.zslin.basic.service.IUserService;
import com.zslin.basic.tools.SecurityUtil;
import com.zslin.basic.utils.BaseSearch;
import com.zslin.basic.utils.PageableUtil;
import com.zslin.basic.utils.SearchDto;
import com.zslin.basic.utils.SortDto;
import com.zslin.web.model.*;
import com.zslin.web.service.*;
import com.zslin.web.tools.EmailTools;
import com.zslin.web.tools.RandomTools;
import com.zslin.web.tools.RequestTools;
import com.zslin.web.tools.SmsTool;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.swing.event.HyperlinkEvent;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
@Controller
public class WebIndexController {

    @Autowired
    private IAccountService accountService;
    @Autowired
    private EmailTools emailTools;
    @Autowired
    private SmsTool smsTool;
    @Autowired
    private ICardService cardService;
    @Autowired
    private IUserService userService;
    @Autowired
    private ISiteService siteService;
    @Autowired
    private IAlarmService alarmService;
    @Autowired
    private IMaintainService maintainService;
    @Autowired
    private ISenddataService senddataService;
    private Map data;
    private String registerCode="";
    @RequestMapping(value={"","/","index"},method=RequestMethod.GET)
    public String index(Model model,HttpServletRequest request){
        return "web/index";
    }
    /** 用户注册 */
    @RequestMapping(value = "register", method = RequestMethod.GET)
    public String register(HttpServletRequest request) {
        return "web/register";
    }

    @RequestMapping(value = "register", method = RequestMethod.POST)
    public String register(Model model, HttpServletRequest request) {
        String email = request.getParameter("email"); //邮箱地址
        String code = request.getParameter("code"); //验证码
        String name = request.getParameter("name"); //用户昵称
        String pwd=request.getParameter("password");//用户的密码
        String registerCode = (String) request.getSession().getAttribute("registerCode");
        Account a = accountService.findByEmail(email);
        if(a!=null) {
            throw new SystemException("邮箱地址【"+email+"】已经存在。如果忘记密码请<a href='/findPwd'>找回密码</a>");
        } else if(!registerCode.equalsIgnoreCase(code)) {
            throw new SystemException("邮箱验证码输入错误！");
        } else {
            /*String pwd = RandomTools.randomCode(); //随机密码*/
            a = new Account();
            a.setEmail(email);
            a.setNickname(name);
            try {
                a.setPassword(SecurityUtil.md5(email, pwd));
            } catch (NoSuchAlgorithmException e) {
            }
            a.setStatus("1");
            accountService.save(a);

            /*emailTools.sendRegisterSuc(email, pwd, RequestTools.getCurUrl(request)+"/web/login");
            emailTools.sendOnRegister(RequestTools.getAdminEmail(request), name, email, "#");*/
        }
        return "redirect:/web/login?reg=true";
    }

    @RequestMapping(value = "appregister", method = RequestMethod.POST)
    public @ResponseBody String appregister(HttpServletRequest request) {
        String email = request.getParameter("email"); //邮箱地址
        String code = request.getParameter("code"); //验证码
        String name = request.getParameter("name"); //用户昵称
        String pwd=request.getParameter("password");//用户的密码
        System.out.println("ok");
        Account a = accountService.findByEmail(email);
        if(a!=null) {
            return "1";
        }else if (registerCode.equals("")){
            return "2";
        }
        else if(!registerCode.equalsIgnoreCase(code)) {
             return "3";
        } else {
            /*String pwd = RandomTools.randomCode(); //随机密码*/
            a = new Account();
            a.setEmail(email);
            a.setNickname(name);
            try {
                a.setPassword(SecurityUtil.md5(email, pwd));
            } catch (NoSuchAlgorithmException e) {
            }
            a.setStatus("1");
            accountService.save(a);

            /*emailTools.sendRegisterSuc(email, pwd, RequestTools.getCurUrl(request)+"/web/login");
            emailTools.sendOnRegister(RequestTools.getAdminEmail(request), name, email, "#");*/
        }
        return "4";
    }
    /** 找回密码 */
    @RequestMapping(value = "findPwd")
    public String findPwd(Model model, HttpServletRequest request) {
        String method = request.getMethod();
        if("get".equalsIgnoreCase(method)) {
            return "web/findPwd";
        } else {
            String email = request.getParameter("email");
            String code = request.getParameter("code");

            if(email==null || code==null) {
                throw new SystemException("请输入邮箱地址和验证码");
            }
            String oldCode = (String) request.getSession().getAttribute("registerCode");
            if(!code.equalsIgnoreCase(oldCode)) {
                throw new SystemException("验证码输入不正确！");
            }

            try {
                String randPwd = RandomTools.randomCode();
                Account a = accountService.findByEmail(email);
                if(a==null) {throw new SystemException("未检索到【"+email+"】对应的用户。");}
                a.setPassword(SecurityUtil.md5(email, randPwd));
                accountService.save(a);

                String url = RequestTools.getCurUrl(request)+"/web/login";
                emailTools.sendFindPwdSuc(email, randPwd, url);

            } catch (NoSuchAlgorithmException e) {
            }

            return "redirect:/web/login";
        }
    }

    /**  用户充值登陆*/
    @RequestMapping(value = "account",method = RequestMethod.GET)
    public String account() {

        return "webm/account/pay2";
    }

    /**  用户充值完成跳转*/
    @RequestMapping(value = "payOk",method = RequestMethod.GET)
    public String payOk() {
        return "webm/account/index";
    }

    /**  后台跳转*/
    @RequestMapping(value = "basic/robot_data",method = RequestMethod.GET)
    public String robot_data() {
        return "pages/robot_data";
    }

    @RequestMapping(value = "basic/flot",method = RequestMethod.GET)
    public String flot() {
        return "pages/flot";
    }

    @RequestMapping(value = "basic/data",method = RequestMethod.GET)
    public String data() {
        return "pages/data";
    }

    @RequestMapping(value = "basic/alarm",method = RequestMethod.GET)
    public String alarm(Model model) {
        Alarm alarm=this.alarmService.findOne(1);
        model.addAttribute("alarm",alarm);
        return "pages/alarm";
    }

    @RequestMapping(value = "basic/maintain",method = RequestMethod.GET)
    public String maintain(Model model) {
//     List<Maintain> maintains=this.maintainService.findAll();
        Maintain maintains=this.maintainService.findOne(25545);
        model.addAttribute("prods",maintains);
        Alarm alarm=this.alarmService.findOne(1);
            model.addAttribute("alarm",alarm);
        return "pages/maintain";
    }

    @RequestMapping(value = "basic/maintaininfo",method = RequestMethod.GET)
    public String maintaininfo(Model model, HttpServletRequest request){
        String deviceId=request.getParameter("deviceId");
        List<Senddata> senddatas=this.senddataService.findByD_id(Integer.parseInt(deviceId));
        model.addAttribute("prods",senddatas);
        model.addAttribute("deviceId",deviceId);
        Alarm alarm=this.alarmService.findOne(1);
        model.addAttribute("alarm",alarm);
        return "pages/maintaininfo";
    }

    @RequestMapping(value = "basic/pay",method = RequestMethod.GET)
    public String pay(Model model,HttpServletRequest request) {
        User user=(User)request.getSession().getAttribute("login_user");
        if (user.getIsAdmin()==1)
        {
            List<Account> accounts=this.accountService.findAll();
            model.addAttribute("prods",accounts);
            int usernumber=this.accountService.usernumber();
            int remainall=this.cardService.remainall();
            model.addAttribute("usernumber",usernumber);
            model.addAttribute("remainall",remainall);
        }
        return "pages/pay";
    }

     /** 站点信息 */
    @RequestMapping(value = "basic/siteinfo")
    public String siteinfo(Model model,HttpServletRequest request) {
        User user=(User)request.getSession().getAttribute("login_user");
        if(user.getIsAdmin()==1){
            List<Site> sites=this.siteService.findAll();
            model.addAttribute("prods",sites);
            return "pages/siteinfo";
        }else{
        List<Site> sites=this.userService.findByUsername(user.getUsername()).getSites();
        model.addAttribute("prods",sites);
        return "pages/siteinfo";
        }
    }

    /** 站点添加 */
    @RequestMapping(value = "basic/addsite")
    public String addsite(Model model) {
           List<User> users=this.userService.findAll();
           model.addAttribute("prods",users);
                return "pages/addsite";
    }

    /** 设备信息  */
    @RequestMapping(value = "basic/deviceinfo")
    public String deviceinfo(Model model, HttpServletRequest request){
         String siteid=request.getParameter("siteid");
         Site site=this.siteService.findById(Integer.parseInt(siteid));
          List<Device> devices=site.getDevices();
          model.addAttribute("prods",devices);
          request.getSession().setAttribute("site",site);
          return "pages/deviceinfo";
    }

    /** 设备添加 */
    @RequestMapping(value = "basic/adddevice")
    public String adddevice(Model model, HttpServletRequest request){
        return "pages/adddevice";
    }


    /** 用户信息　*/
    @RequestMapping(value = "basic/userinfo")
    public String userinfo(Model model,HttpServletRequest request){
        User user=(User)request.getSession().getAttribute("login_user");
        if(user.getIsAdmin()==1){
            List<User> users=this.userService.findAll();
            model.addAttribute("prods",users);
            return "pages/userinfo";
        }else{
            model.addAttribute("prods",user);
            return "pages/userinfo";
        }
    }

    /** 添加用户 */
    @RequestMapping(value = "basic/adduser")
    public String adduser(){
        return "pages/adduser";
    }

    /** 用户信息修改 */
    @RequestMapping(value = "basic/verifyuser")
    public String verifyuser(Model model,HttpServletRequest request){
        String username=request.getParameter("userid");
        User user=this.userService.findByUsername(username);
        model.addAttribute("user",user);
        return "pages/verifyuser";
    }

    /** app用户登录*/
    @RequestMapping(value = "app/login")
    @ResponseBody
    public JSONObject applogin(@RequestParam String username,@RequestParam String password,HttpServletRequest request){
        String method = request.getMethod();
        String Cardnumber;
        data=new LinkedHashMap();
        System.out.println(username+password);
        if("post".equalsIgnoreCase(method)) {
            if(username.equals("") || password.equals("")) {
                data.put("state","fail");
                JSONObject result = JSONObject.fromObject(data);
                return result;
            }
            Account account=this.accountService.findByEmail(username);

            if(account==null || account.getStatus().equals("0")) {
                data.put("state","fail");
                JSONObject result = JSONObject.fromObject(data);
                return result;
            }
            try {
                String pwd = SecurityUtil.md5(username, password);
                if(!pwd.equals(account.getPassword())) {
                    data.put("state","fail");
                    JSONObject result = JSONObject.fromObject(data);
                    return result;
                } else {
                    Accountinfo accountinfo=new Accountinfo();
                    accountinfo.setEmail(username);
                    if (account.getCard()==null){
                        accountinfo.setRest("0");
                        accountinfo.setCardnumber("");
                    }else{

                        accountinfo.setRest(account.getCard().getRest().toString());
                        Cardnumber = account.getCard().getCid();
                        Long newCardnumber=Long.parseLong(Cardnumber);
                        newCardnumber=newCardnumber - 3030303030303030L;
                        accountinfo.setCardnumber(String.format("%08d", newCardnumber));
                    }
                    data.put("state","success");
                    data.put("userinfo",accountinfo);
                    JSONObject result = JSONObject.fromObject(data);
                    return result;
                }
            } catch (NoSuchAlgorithmException e) {
                data.put("state","fail");
                JSONObject result = JSONObject.fromObject(data);
                return result;
            }
        } else {
            data.put("state","fail");
            JSONObject result = JSONObject.fromObject(data);
            return result;
        }
    }

    /** 用户登陆 */
    @RequestMapping(value = "/appmanage/login")
    @ResponseBody
    public JSONObject appmanagelogin(@RequestParam String username,@RequestParam String password,HttpServletRequest request) {
        String method = request.getMethod();
        data=new LinkedHashMap();
        if("post".equalsIgnoreCase(method)) {
            if(username==null || password==null) {
                data.put("state","usernameorpasswordNull");
                JSONObject result = JSONObject.fromObject(data);
                return result;
            }
            User user = userService.findByUsername(username);
            if(user==null || user.getStatus()==0) {
                data.put("state","fail");
                JSONObject result = JSONObject.fromObject(data);
                return result;
            }
            try {
                String pwd = SecurityUtil.md5(username, password);
                if(!pwd.equals(user.getPassword())) {
                    data.put("state","fail");
                    JSONObject result = JSONObject.fromObject(data);
                    return result;
                } else {
                    data.put("state","success");
                    data.put("isadmin",user.getIsAdmin().toString());
                    JSONObject result = JSONObject.fromObject(data);
                    return result;
                }
            } catch (NoSuchAlgorithmException e) {
                data.put("state","fail");
                JSONObject result = JSONObject.fromObject(data);
                return result;
            }
        } else {
            data.put("state","fail");
            JSONObject result = JSONObject.fromObject(data);
            return result;
        }
    }

    /** 用户登陆 */
    @RequestMapping(value = "/web/login")
    public String login(Model model, HttpServletRequest request) {
        String method = request.getMethod();
        if("post".equalsIgnoreCase(method)) {
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            if(email==null || password==null) {
                throw new SystemException("请输入邮箱地址和密码登陆。");
            }
            Account account = accountService.findByEmail(email);
            if(account==null || !account.getStatus().equalsIgnoreCase("1")) {
                throw new SystemException("账户【"+email+"】不存在或已被停用。");
            }
            try {
                String pwd = SecurityUtil.md5(email, password);
                if(!pwd.equals(account.getPassword())) {
                    throw new SystemException("密码输入错误！");
                } else {
                    request.getSession().setAttribute("login_account", account);
                }
                return "redirect:/webm/account/index";
            } catch (NoSuchAlgorithmException e) {
            }
        } else {
            Account account = (Account) request.getSession().getAttribute("login_account");
            if(account!=null) {
                return "redirect:/webm/account/index";
            }
        }
        return "web/login";
    }

    /** 退出 */
    @RequestMapping(value = "/web/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request) {
        request.getSession().removeAttribute("login_account");
        return "redirect:/";
    }

    /** 发送邮箱验证码 */
    @RequestMapping(value = "sendCode", method = RequestMethod.POST)
    public @ResponseBody String sendCode(String email, HttpServletRequest request) {
        try {
            String code = RandomTools.randomCode();
            registerCode=code;
            request.getSession().setAttribute("registerCode", code);
            if(email.contains("@")){
                emailTools.sendRegisterCode(email, code);
            }
            else{
                smsTool.sendRegisterCode(email,code);
            }
            /*smsTool.sendRegisterCode(email,code);*/
        } catch (Exception e) {
            return "0";
        }
        return "1";
    }


}

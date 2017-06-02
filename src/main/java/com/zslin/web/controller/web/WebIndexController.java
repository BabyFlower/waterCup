package com.zslin.web.controller.web;

import com.zslin.basic.exception.SystemException;
import com.zslin.basic.tools.SecurityUtil;
import com.zslin.basic.utils.BaseSearch;
import com.zslin.basic.utils.PageableUtil;
import com.zslin.basic.utils.SearchDto;
import com.zslin.basic.utils.SortDto;
import com.zslin.web.model.Account;
import com.zslin.web.service.IAccountService;
import com.zslin.web.tools.EmailTools;
import com.zslin.web.tools.RandomTools;
import com.zslin.web.tools.RequestTools;
import com.zslin.web.tools.SmsTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.security.NoSuchAlgorithmException;

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
    @RequestMapping(value = "/basic/robot_data",method = RequestMethod.GET)
    public String robot_data() {
        return "/pages/robot_data";
    }

    @RequestMapping(value = "/basic/flot",method = RequestMethod.GET)
    public String flot() {
        return "/pages/flot";
    }

    @RequestMapping(value = "/basic/data",method = RequestMethod.GET)
    public String data() {
        return "/pages/data";
    }

    @RequestMapping(value = "/basic/alarm",method = RequestMethod.GET)
    public String alarm() {
        return "/pages/alarm";
    }

    @RequestMapping(value = "/basic/maintain",method = RequestMethod.GET)
    public String maintain() {
        return "/pages/maintain";
    }

    @RequestMapping(value = "/basic/pay",method = RequestMethod.GET)
    public String pay() {
        return "/pages/pay";
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

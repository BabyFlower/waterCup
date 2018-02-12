package com.zslin.web.controller.web;

import com.zslin.basic.utils.*;
import com.zslin.web.dto.CateDto;
import com.zslin.web.model.Account;
import com.zslin.web.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 *
 */
@Controller
@RequestMapping(value = "web/account")
public class WebAccountController {

    @Autowired
    private IAccountService accountService;
    @RequestMapping(value = "index", method = RequestMethod.GET)
    public String index(Model model, Integer page, HttpServletRequest request) {
        Page<Account> datas = accountService.findAll(new ParamFilterUtil<Account>().buildSearch(model, request),
                PageableUtil.basicPage(page, "id"));
        model.addAttribute("datas", datas);
        return "web/account/index";
    }
}

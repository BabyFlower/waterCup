package com.zslin.web.controller.admin;


import com.zslin.basic.model.User;
import com.zslin.basic.service.IUserService;
import com.zslin.web.model.Account;
import com.zslin.web.model.Card;
import com.zslin.web.service.IAccountService;
import com.zslin.web.service.ICardService;
import net.sf.json.JSONObject;
import org.hibernate.validator.constraints.CreditCardNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/6.
 */
@Controller
public class CardController {
    @Autowired
    private ICardService cardService;
    @Autowired
    private IAccountService accountService;

    @RequestMapping(value = "/card/register", method = RequestMethod.POST)
    public String cardRegister(@RequestParam String username, @RequestParam String cardnumber, Model model, HttpServletRequest request){
        StringBuilder  cn = new StringBuilder (cardnumber);
        cn.insert(7, "3");
        cn.insert(6, "3");
        cn.insert(5, "3");
        cn.insert(4, "3");
        cn.insert(3, "3");
        cn.insert(2, "3");
        cn.insert(1, "3");
        cn.insert(0, "3");
        String CardNumber = cn.toString();
        Card card=this.cardService.findByCid(CardNumber);
        if (card==null){
             model.addAttribute("cardstate","卡号无效");
             return "webm/account/index";
        }else{
            model.addAttribute("cardstate","绑定成功");
            Account account=this.accountService.findByEmail(username);
            account.setCard(card);
           accountService.save(account);
            return "webm/account/index";
        }
    }


}

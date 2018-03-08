package com.zslin.web.controller.admin;

import com.zslin.web.model.Account;
import com.zslin.web.model.Card;
import com.zslin.web.service.IAccountService;
import com.zslin.web.service.ICardService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/1.
 */
@RestController

public class AppCardController {
    @Autowired
    private ICardService cardService;
    @Autowired
    private IAccountService accountService;
    private Map data;
    @RequestMapping(value = "/appcard/register", method = RequestMethod.POST)
    public JSONObject appcardRegister(@RequestParam String username, @RequestParam String cardnumber){
        StringBuilder  cn = new StringBuilder (cardnumber);
        System.out.println(username);
        System.out.println(cardnumber);
        data=new LinkedHashMap();
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
            data.put("state","failed");
            JSONObject result = JSONObject.fromObject(data);
            return result;

        }else{
            Account account=this.accountService.findByEmail(username);
            account.setCard(card);
            accountService.save(account);
            data.put("state","success");
            JSONObject result = JSONObject.fromObject(data);
            return result;
        }
    }
}

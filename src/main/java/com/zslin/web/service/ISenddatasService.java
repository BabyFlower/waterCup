package com.zslin.web.service;

import com.zslin.web.model.Senddata;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by xch on 2017/4/22.
 */
@Service
public class ISenddatasService {
    @Autowired
    ISenddataService iSenddataService;
    public void save(Senddata sd){
        this.iSenddataService.save(sd);
    }

}

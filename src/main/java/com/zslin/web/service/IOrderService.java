package com.zslin.web.service;

import com.zslin.web.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2017/6/22.
 */
@Service
public interface IOrderService extends JpaRepository<Order, Integer>, JpaSpecificationExecutor<Order> {

    Order findByOrdernumber(String ordernumber);
}

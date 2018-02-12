package com.zslin.web.service;

import com.zslin.web.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2017/6/6.
 */
@Service
public interface ICardService extends JpaRepository<Card, Integer>, JpaSpecificationExecutor<Card> {
    Card findByCid(String cid);
    @Query("SELECT SUM(rest) FROM Card ")
    Integer remainall();
}

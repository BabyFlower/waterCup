package com.zslin.web.service;

import com.zslin.web.model.Site;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Ji on 2017/3/23.
 */
public interface ISendsiteService  extends JpaRepository<Site,Integer> {
    /*查找同一字段*/
   /* @Query("FROM  Site sd where sd.s_id>=:sid")
   // List<Site> find(@Param("sid") Integer sid);
    Site find(@Param("sid") Integer sid);*/
/*
    @Query("FROM  Site sd where sd.s_id>=:sid")
        // List<Site> find(@Param("sid") Integer sid);
    List<Site> findBy(@Param("sid") Integer sid);*/

    @Query("FROM Site")
    List<Site> findAll();
}
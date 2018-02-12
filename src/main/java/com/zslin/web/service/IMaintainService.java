package com.zslin.web.service;


import com.zslin.web.model.Maintain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Created by Administrator on 2017/6/19.
 */
@Service
public interface IMaintainService extends JpaRepository<Maintain, Integer>, JpaSpecificationExecutor<Maintain> {
/*    @Query("FROM Maintain")
//    List<Maintain> findById(Integer id);
    Maintain findById(Integer id);*/
}

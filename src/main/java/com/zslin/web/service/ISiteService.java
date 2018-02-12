package com.zslin.web.service;

import com.zslin.basic.model.User;
import com.zslin.web.model.Senddata;
import com.zslin.web.model.Site;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Ji on 2017/3/23.
 */
@Service
public interface ISiteService extends JpaRepository<Site, Integer>, JpaSpecificationExecutor<Site>{
    Site findById(Integer id);
}

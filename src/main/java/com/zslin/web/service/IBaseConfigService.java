package com.zslin.web.service;

import com.zslin.web.model.BaseConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service("baseConfigService")
public interface IBaseConfigService extends JpaRepository<BaseConfig, Integer> {

    @Query("FROM BaseConfig ")
    public BaseConfig loadOne();
}

package com.zslin.web.service;

import com.zslin.web.model.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by Administrator on 2017/6/19.
 */
public interface IAlarmService extends JpaRepository<Alarm, Integer>, JpaSpecificationExecutor<Alarm> {


}

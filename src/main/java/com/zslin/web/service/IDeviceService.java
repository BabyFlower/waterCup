package com.zslin.web.service;


import com.zslin.web.model.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Ji on 2017/3/23.
 */

@Repository
public interface IDeviceService extends JpaRepository<Device,Integer>{

    @Query("FROM Device d where d.model_id=:id")
   Device findBymodel_id(@Param("id") Long id);
    /**通过站点号查询设备的信息*/
 /*   @Query("FROM Device d where d.s_id=:sid")
    List<Device> findDeviceBySId(@Param("sid") Integer sid);
    *//**通过设备号查询设备的状态*//*
    @Query("FROM Device d where d.d_id=:did")
    Device findDeviceBydId(@Param("did") Long did);*/
}
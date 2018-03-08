package com.zslin.web.service;

import com.zslin.web.bean.DatatableRequest;
import com.zslin.web.model.Senddata;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Ji on 2017/3/23.
 */
@Repository
/*public interface ISenddataService  extends JpaRepository<Senddata,Integer> {*/

//,QueryDslPredicateExecutor<Senddata>
public interface ISenddataService  extends PagingAndSortingRepository<Senddata,Integer> {
    /*查找某一个站点的所有数据___________这里应该是一个设备的所有数据（by谢承翰）*/
    @Query("FROM  Senddata sd where sd.d_id=:did" )
    List<Senddata> findByD_id(@Param("did") Integer did);

  /*  降序查找*/
    @Query("FROM  Senddata sd where sd.d_id=:did ORDER BY sentem desc")
/* Page<Senddata> findByD_id( DatatableRequest request,@Param("did") Integer did);*/
    Page<Senddata> findByD_id( Pageable pageable,@Param("did") Integer did);



}

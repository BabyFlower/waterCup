package com.zslin.web.service;

import com.zslin.web.model.Account;
import com.zslin.web.model.Device;
import org.hibernate.sql.Update;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 *
 */
public interface IAccountService extends JpaRepository<Account, Integer>, JpaSpecificationExecutor<Account> {

    /** 通过邮箱获取对象 */
    Account findByEmail(String email);
    Account findByNickname(String nickname);
    @Query("SELECT COUNT(*) FROM Account")
    Integer usernumber();
 }
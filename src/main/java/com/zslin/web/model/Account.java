package com.zslin.web.model;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.List;

/**
 *
 */

@Data
@Entity
@Table(name = "t_account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String email;

    private String nickname;

    /** 座右铭 */
    private String motto;

    @Lob
    private String remark;

    /** 状态 */
    private String status;

    /** 头像 */
    private String headimg;

    private String password;

    private String remain;

    /**  充值金额*/
   private String paymoney;

    /**  充值状态：0为未成功，1为成功，null为初始值*/
   private String paystate;

   @ManyToOne
    private Card card;

   @OneToMany(mappedBy = "account")
    private List<Order> orders;
}

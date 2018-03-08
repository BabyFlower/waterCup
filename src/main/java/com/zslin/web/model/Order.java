package com.zslin.web.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

/**
 * Created by Administrator on 2017/6/22.
 */


@Data
@Entity
@Table(name = "t_order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String ordernumber;
    private String fee;
    private String time;
    private String state;
    private String paymethod;
    @ManyToOne
    private Account account;
}

package com.zslin.web.model;

import lombok.Data;
import javax.persistence.*;
import java.util.List;

/**
 * Created by Administrator on 2017/6/5.
 */
@Data
@Entity
@Table(name = "t_card")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String deviceid;
    private String uid;
    private String cid;
    private Float charge;
    private Float rest;
    private String paystate;
    @OneToMany(mappedBy = "card")
    private List<Account> accounts;
}


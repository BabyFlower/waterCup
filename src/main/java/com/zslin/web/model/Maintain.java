package com.zslin.web.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

/**
 * Created by Administrator on 2017/6/19.
 */

@Data
@Entity
@Table(name = "maintain")
public class Maintain {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Float suwd;
    private Float suyl;
    private Float jsll;
    private Float csll;
    private Float jstds;
    private Float cstds;
    private Float ph;
    private Float yl;
    private Float ylv;
    private Float ycy;
    private Float zd;
    private Float rjy;
    private String sentem;
    private String sitename;
    private String devicename;
    private Integer deviceId;

}

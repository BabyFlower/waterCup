package com.zslin.web.model;

import lombok.*;

import javax.persistence.*;

/**
 * Created by Ji on 2017/3/16.
 */
@Data
@Entity
@Table(name = "t_device")
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String sn;
    private Long model_id;
    private int status;
    //private  int s_id;
    private String remainder;
    private String name;
    @ManyToOne
    private Site site;

}

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
@Table(name = "alarm")
public class Alarm {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Float suwdup;
    private Float suylup;
    private Float jsllup;
    private Float csllup;
    private Float jstdsup;
    private Float cstdsup;
    private Float phup;
    private Float ylup;
    private Float ylvup;
    private Float ycyup;
    private Float zdup;
    private Float rjyup;
    private Float suwddown;
    private Float suyldown;
    private Float jslldown;
    private Float cslldown;
    private Float jstdsdown;
    private Float cstdsdown;
    private Float phdown;
    private Float yldown;
    private Float ylvdown;
    private Float ycydown;
    private Float zddown;
    private Float rjydown;
}

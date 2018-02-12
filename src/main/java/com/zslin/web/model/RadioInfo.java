package com.zslin.web.model;

import javax.persistence.*;

/**
 * Created by Ji on 2017/3/16.
 */
@Entity
@Table(name = "d_radio_info")
public class RadioInfo {

    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String sn;
    private String imei;
    @Id
    public Long getId() {return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }


}

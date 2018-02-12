package com.zslin.web.model;

import org.codehaus.groovy.runtime.StringGroovyMethods;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by Ji on 2017/3/16.
 */
@Entity
@Table(name = "sendata")
public class Senddata implements Comparable<Senddata>{
    @Id

    @GeneratedValue(strategy = GenerationType.AUTO)
    private  Integer id;
    private Integer se_id;
    private String suwd;
    private String suyl;
    private String jsll;
    private String csll;
    private String jstds;
    private String cstds;
    private String ph;
    private String yl;
    private String ylv;
    private String ycy;
    private String zd;
    private String rjy;
    private Integer d_id;
    private String sentem;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Integer getSe_id() {
        return se_id;
    }

    public void setSe_id(Integer se_id) {
        this.se_id = se_id;
    }

    public String getSuwd() {
        return suwd;
    }

    public void setSuwd(String suwd) {
        this.suwd = suwd;
    }

    public String getSuyl() {
        return suyl;
    }

    public void setSuyl(String suyl) {
        this.suyl = suyl;
    }

    public String getJsll() {
        return jsll;
    }

    public void setJsll(String jsll) {
        this.jsll = jsll;
    }
    public String getCsll(){
       return csll;
    }
    public void setCsll(String csll){
        this.csll=csll;
    }

    public String getJstds() {
        return jstds;
    }

    public void setJstds(String jstds) {
        this.jstds = jstds;
    }

    public String getCstds() {
        return cstds;
    }

    public void setCstds(String cstds) {
        this.cstds = cstds;
    }

    public String getPh() {
        return ph;
    }

    public void setPh(String ph) {
        this.ph = ph;
    }

    public String getYl() {
        return yl;
    }

    public void setYl(String yl) {
        this.yl = yl;
    }

    public String getYlv() {
        return ylv;
    }

    public void setYlv(String ylv) {
        this.ylv = ylv;
    }

    public String getYcy() {
        return ycy;
    }

    public void setYcy(String ycy) {
        this.ycy = ycy;
    }

    public String getZd() {
        return zd;
    }

    public void setZd(String zd) {
        this.zd = zd;
    }

    public String getRjy() {
        return rjy;
    }

    public void setRjy(String rjy) {
        this.rjy = rjy;
    }

    public Integer getD_id() {
        return d_id;
    }

    public void setD_id(Integer d_id) {
        this.d_id = d_id;
    }

    public String getSentem() {
        return sentem;
    }

    public void setSentem(String sentem) {
        this.sentem = sentem;
    }

    public int compareTo(Senddata  o) {
        // TODO Auto-generated method stub
        if(Long.valueOf(this.sentem)>Long.valueOf(o.getSentem())){
            return -1;
        }
        else if(Long.valueOf(this.sentem)<Long.valueOf(o.getSentem())){
            return 1;
        }
        else{
            return 0;
        }
    }

}

package com.zslin.web.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.zslin.basic.model.User;
import lombok.*;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Ji on 2017/3/16.
 */
@Data
@Entity
@Table(name = "t_site")
public class Site{

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Integer id;
        private String sitephone;
        //private Integer s_id;
        private String name;
        private String phonenumber;

    @OneToMany(mappedBy = "site")
    private List<Device> devices;

    @ManyToOne
     private User user;


}

package com.zslin.basic.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.zslin.web.model.Site;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 *
 */
@Data
@Entity
@Table(name="a_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String username;
    private String password;
    private Integer status;
    @Column(name="create_date")
    private Date createDate;
    /** 用户昵称 */
    private String nickname;

    @Column(name="is_admin")
    private Integer isAdmin;

    @OneToMany(mappedBy = "user")
/*    @JsonManagedReference*/
    private List<Site> sites;

   /* public User() {
    }

    protected boolean canEqual(Object other) {
        return other instanceof User;
    }
*/
}

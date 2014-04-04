/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gian.entities;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Gianluca Tessitore
 */
@Entity
@Table(name = "tab_authorization")
public class Authorization implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id_auth")
    private Long id_auth;
    
    @OneToOne
    @JoinColumn(name = "id")
    private User user;
    
    @Column(name = "type")
    private boolean type;

    public Long getId_auth() {
        return id_auth;
    }

    public void setId_auth(Long id_auth) {
        this.id_auth = id_auth;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean getType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }
    
    
    
}

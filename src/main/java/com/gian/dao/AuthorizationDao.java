/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gian.dao;

import com.gian.entities.Authorization;
import com.gian.entities.User;
import java.util.List;

/**
 *
 * @author Gian
 */
public interface AuthorizationDao extends GenericDao<Authorization, Long>{
    
    public List<User> getAuthorizedUser();
    
}

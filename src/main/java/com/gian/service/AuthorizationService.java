/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gian.service;

import com.gian.entities.Authorization;
import com.gian.entities.User;
import java.util.List;

/**
 *
 * @author Gian
 */
public interface AuthorizationService {

    //Native Methods
    
    public void addAuthorization(Authorization a);
    
    public Authorization getAuthorization(Long authorizationId);
    
    public void updateAuthorization(Authorization a);
    
    public void deleteAuthorization(Authorization a);
    
    public List<Authorization> getAll();
    
    //Others 
    
    public List<User> getAuthorizedUser();
    
}

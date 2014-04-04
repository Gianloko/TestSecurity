/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gian.service.impl;

import com.gian.dao.AuthorizationDao;
import com.gian.entities.Authorization;
import com.gian.entities.User;
import com.gian.service.AuthorizationService;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Gian
 */
@Service("authorizationService")
@Transactional(rollbackFor = {Exception.class})
public class AuthorizationServiceImpl implements AuthorizationService {

    private final Logger _log = Logger.getLogger(UserServiceImpl.class);

    @Autowired
    private AuthorizationDao authorizationDao;
    
    //Native Methods
    
    @Override
    public void addAuthorization(Authorization a) {
        authorizationDao.create(a);
    }

    @Override
    public Authorization getAuthorization(Long authorizationId) {
        return authorizationDao.read(Authorization.class, authorizationId);
    }

    @Override
    public void updateAuthorization(Authorization a) {
        authorizationDao.update(a);
    }

    @Override
    public void deleteAuthorization(Authorization a) {
        authorizationDao.delete(a);
    }

    @Override
    public List<Authorization> getAll(){
        return authorizationDao.getAll(Authorization.class);
    }
    
    //Others
    
    @Override
    public List<User> getAuthorizedUser(){
        return authorizationDao.getAuthorizedUser();
    }
    
}
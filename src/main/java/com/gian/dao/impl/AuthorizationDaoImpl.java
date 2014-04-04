/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gian.dao.impl;

import com.gian.dao.AuthorizationDao;
import com.gian.entities.Authorization;
import com.gian.entities.User;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Gian
 */
@Repository
public class AuthorizationDaoImpl extends GenericDaoImpl<Authorization, Long> implements AuthorizationDao{
    
    private final Logger _log = Logger.getLogger(AuthorizationDaoImpl.class);
    
    @Override
    public List<User> getAuthorizedUser(){
        
        //Get a list of User also by Criteria
        
        List<User> listUser = null;
        
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        _log.info("Info: get only authorized users...");
        try {
            transaction = session.beginTransaction();
            Criteria criteria = session.createCriteria(Authorization.class)
            .setProjection(Projections.property("user"))
            .add(Restrictions.eq("type", true));
            listUser = (List<User>) criteria.list();
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
                _log.error(e);
            } else {
                _log.error("Transaction is null");
            }
        } finally {
            session.close();
        }
        return listUser;
    
    }
    
}

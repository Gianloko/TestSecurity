package com.gian.dao.impl;

import com.gian.dao.GenericDao;
import java.io.Serializable;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Francesco Arciello
 * @param <T>
 * @param <PK>
 */
public class GenericDaoImpl<T, PK extends Serializable> implements GenericDao<T, PK> {

    private static final Logger _log = Logger.getLogger(GenericDaoImpl.class);
    @Autowired
    protected SessionFactory sessionFactory;

    @Override
    public PK create(T t) {
        PK object = null;
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            object = (PK) session.save(t);      
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
        return object;
    }

    @Override
    public T read(Class type, PK id) {
        T object = null;
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            object = (T) session.get(type, id);
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
        return object;
    }

    @Override
    public void update(T t) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            t = (T) session.merge(t);
            session.saveOrUpdate(t);
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
    }

    @Override
    public void delete(T t) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            t = (T) session.merge(t);
            session.delete(t);
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
    }

    @Override
    public List<T> getAll(Class type) {
        List<T> objects = null;
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Criteria criteria = session.createCriteria(type);
            objects = criteria.list();
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
        return objects;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}

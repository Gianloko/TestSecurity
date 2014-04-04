package com.gian.dao.impl;


import com.gian.dao.UserDao;
import com.gian.entities.User;
import com.gian.utils.GenerateUtils;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.logging.Level;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Francesco Arciello
 */
@Repository
public class UserDaoImpl extends GenericDaoImpl<User, Long> implements UserDao {

    private final Logger _log = Logger.getLogger(UserDaoImpl.class);
    
    @Override
    public boolean loginUser(String userName, String password) {
        
        GenerateUtils gu = new GenerateUtils();
        boolean exist = false;
        User user = null;
        
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        _log.info("userName: " + userName);
        try {
            transaction = session.beginTransaction();           
            String q = "FROM User u where u.login = :userName and u.password = :password";
            Query query = session.createQuery(q);
            query.setString("userName", userName);
            query.setString("password", gu.hashMD5(password));
            user = (User) query.uniqueResult();
            if(user != null){             
                exist = true;
            }
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
                _log.error(e);
            } else {
                _log.error("Transaction is null");
            }
        } catch (NoSuchAlgorithmException ex) {
            java.util.logging.Logger.getLogger(UserDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            session.close();
        }
        return exist;
    }   
    
    @Override
    public User getUserByUsername(String userName) {
        
        User user = null;
        
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        _log.info("userName: " + userName);
        try {
            transaction = session.beginTransaction();           
            String q = "FROM User u where u.login = :userName";
            Query query = session.createQuery(q);
            query.setString("userName", userName);
            user = (User) query.uniqueResult();
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
        return user;
    }

    @Override
    public List<User> getUserByName(String userName) {
        
        List<User> listUser = null;
        
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        _log.info("Name: " + userName);
        try {
            transaction = session.beginTransaction();           
            String q = "FROM User u where u.name = :userName";
            Query query = session.createQuery(q);
            query.setString("name", userName);
            listUser = (List<User>) query.list();
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

    @Override
    public List<User> getUserByLastname(String lastname) {
        
        List<User> listUser = null;
        
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        _log.info("Lastname: " + lastname);
        try {
            transaction = session.beginTransaction();           
            String q = "FROM User u where u.lastname = :lastname";
            Query query = session.createQuery(q);
            query.setString("lastname", lastname);
            listUser = (List<User>) query.list();
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

    @Override
    public List<User> getUserByAge(Long age) {
        
        List<User> listUser = null;
        
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        _log.info("Age: " + age);
        try {
            transaction = session.beginTransaction();           
            String q = "FROM User u where u.age = :age";
            Query query = session.createQuery(q);
            query.setString("age", age.toString());
            listUser = (List<User>) query.list();
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

    @Override
    public User getUserByPhone(String phone) {
        
        User user = null;
        
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        _log.info("Phone: " + phone);
        try {
            transaction = session.beginTransaction();           
            String q = "FROM User u where u.phone = :phone";
            Query query = session.createQuery(q);
            query.setString("phone", phone);
            user = (User) query.uniqueResult();
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
        return user;
        
    }

    @Override
    public List<User> getUserByAddress(String address) {
        
        List<User> listUser = null;
        
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        _log.info("Address: " + address);
        try {
            transaction = session.beginTransaction();           
            String q = "FROM User u where u.address = :address";
            Query query = session.createQuery(q);
            query.setString("address", address);
            listUser = (List<User>) query.list();
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

    @Override
    public User getUserByEmail(String email) {
        
        User user = null;
        
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        _log.info("Email: " + email);
        try {
            transaction = session.beginTransaction();           
            String q = "FROM User u where u.email = :email";
            Query query = session.createQuery(q);
            query.setString("email", email);
            user = (User) query.uniqueResult();
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
        return user;
        
    }

    @Override
    public List<User> getUserBySex(String sex) {
        
        List<User> listUser = null;
        
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        _log.info("Sex: " + sex);
        try {
            transaction = session.beginTransaction();           
            String q = "FROM User u where u.sex = :sex";
            Query query = session.createQuery(q);
            query.setString("sex", sex);
            listUser = (List<User>) query.list();
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

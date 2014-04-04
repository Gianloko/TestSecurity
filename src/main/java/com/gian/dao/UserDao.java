package com.gian.dao;

import com.gian.entities.User;
import java.util.List;


/**
 *
 * @author Francesco Arciello
 */
public interface UserDao extends GenericDao<User, Long> {
    
    public boolean loginUser(String userName, String password);
    
    public User getUserByUsername(String userName);
    
    public List<User> getUserByName(String name);
    
    public List<User> getUserByLastname(String lastname);
    
    public List<User> getUserByAge(Long age);
    
    public User getUserByPhone(String phone);
    
    public List<User> getUserByAddress(String address);
    
    public User getUserByEmail(String email);
    
    public List<User> getUserBySex(String sex);
    
}

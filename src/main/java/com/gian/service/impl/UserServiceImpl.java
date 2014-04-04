package com.gian.service.impl;
import com.gian.dao.UserDao;
import com.gian.entities.User;
import com.gian.service.UserService;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service("userService")
@Transactional(rollbackFor = {Exception.class})
public class UserServiceImpl implements UserService {

    private final Logger _log = Logger.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;
    
    //Native Methods
    
    @Override
    public void addUser(User u) {
        userDao.create(u);
    }

    @Override
    public User getUser(Long userId) {
        return userDao.read(User.class, userId);
    }

    @Override
    public void updateUser(User u) {
        userDao.update(u);
    }

    @Override
    public void deleteUser(User u) {
        userDao.delete(u);
    }

    @Override
    public List<User> getAll(){
        return userDao.getAll(User.class);
    }
    
    //Others

    @Override
    public boolean loginUser(String userName, String password){
        return userDao.loginUser(userName, password);
    }
    
    @Override
    public User getUserByUsername(String userName){
        return userDao.getUserByUsername(userName);
    }

    @Override
    public List<User> getUserByName(String name) {
        return userDao.getUserByName(name);
    }

    @Override
    public List<User> getUserByLastname(String lastname) {
        return userDao.getUserByLastname(lastname);
    }

    @Override
    public List<User> getUserByAge(Long age) {
        return userDao.getUserByAge(age);
    }

    @Override
    public User getUserByPhone(String phone) {
        return userDao.getUserByPhone(phone);
    }

    @Override
    public List<User> getUserByAddress(String address) {
        return userDao.getUserByAddress(address);
    }

    @Override
    public User getUserByEmail(String email) {
        return userDao.getUserByEmail(email);
    }

    @Override
    public List<User> getUserBySex(String sex) {
        return userDao.getUserBySex(sex);
    }
    
}

package com.gian.service;
import com.gian.entities.User;
import java.util.List;

public interface UserService {

    //Native Methods
    
    public void addUser(User u);
    
    public User getUser(Long userId);
    
    public void updateUser(User u);
    
    public void deleteUser(User u);
    
    public List<User> getAll();
    
    //Others 
    
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

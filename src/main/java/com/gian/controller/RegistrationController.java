/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gian.controller;

import com.gian.entities.Authorization;
import com.gian.entities.User;
import com.gian.message.JsonResponse;
import com.gian.service.AuthorizationService;
import com.gian.service.UserService;
import com.gian.utils.GenerateUtils;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Gianluca Tessitore
 */
@Controller
@RequestMapping("register")
public class RegistrationController {
    
    private final Logger _log = Logger.getLogger(RegistrationController.class);
    
    @Autowired
    UserService userService;
    
    @Autowired
    AuthorizationService authorizationService;
    
    @RequestMapping(value = "addUser", method = RequestMethod.POST)
    public @ResponseBody ModelAndView addUser(@RequestParam String name, @RequestParam String lastname, @RequestParam Long age, @RequestParam String phone,
                                @RequestParam String address, @RequestParam String email, @RequestParam String password, @RequestParam String sex) throws NoSuchAlgorithmException {
        
        Calendar now = Calendar.getInstance();
        _log.info("Adding new User: " + name);
        
        GenerateUtils gu = new GenerateUtils();
        User u = new User();          
        
        //Add user info
        
        u.setName(name);
        u.setLastname(lastname);
        u.setAge(age);
        u.setPhone(phone);
        u.setAddress(address);
        u.setEmail(email);
        u.setSessionStartTime(now);
        u.setStartTime(now.getTimeInMillis());
        u.setSeed(null);
        u.setRole(null);
        u.setSex(sex);
        u.setLogin(gu.generateUsername(name));
        u.setPassword(gu.hashMD5(password));
        userService.addUser(u);
        
        _log.info("User created!");
        
        //Add User Authorization
        
        Authorization auth = new Authorization();
        auth.setUser(userService.getUser(u.getId()));
        auth.setType(false);
        authorizationService.addAuthorization(auth);
        
        _log.info("Authorization set successful!");
        
        return new JsonResponse("StatusId", u.getId().toString() + " created!", "AuthorizationUser", "set!").asModelAndView();
        
    }
    
    @RequestMapping(value = "getAuthorizedUser", method = RequestMethod.GET)
    public @ResponseBody List<User> getAuthorizedUsers(){
    
        return (List<User>)authorizationService.getAuthorizedUser();
    
    }
    
}

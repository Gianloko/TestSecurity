/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gian.controller;

import com.gian.service.UserService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Gianluca Tessitore
 */

@Controller
@RequestMapping("security")
public class SecurityController {
    
    private final Logger _log = Logger.getLogger(SecurityController.class);
    
    @Autowired
    UserService userService;
    
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public ModelAndView login(HttpServletRequest request, @RequestParam String login, @RequestParam String password){
        
        ModelAndView mv = null;
        boolean b = userService.loginUser(login, password);
        
        if(b == true){
            
            //Create a session variable for login
            
            HttpSession stateSession = request.getSession(true);
            stateSession.setAttribute("id", "logged");
            
            _log.info("Start Login Session!");
            mv = new ModelAndView("home");
            mv.addObject("user", login);

        }else{
            
            HttpSession stateSession = request.getSession();
            stateSession.removeAttribute("id");
            
            _log.info("Login Failed!");
            mv = new ModelAndView("index");
            mv.addObject("messageFromController", "Login Failed!");
        }
        
        return mv;
    
    }
    
    @RequestMapping(value = "logout", method = RequestMethod.POST)
    public ModelAndView logout(HttpServletRequest request){
    
        HttpSession stateSession = request.getSession();
        stateSession.removeAttribute("id");
        
        _log.info("Session Logout...");
        ModelAndView mv = new ModelAndView("index");
        mv.addObject("messageFromController", "SecurityController Logout!");
        return mv;
    
    }
    
}

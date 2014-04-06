/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gian.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Gianluca Tessitore
 */
@Controller
public class DefaultController {
    
    private final Logger _log = Logger.getLogger(DefaultController.class);
    
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(ModelMap map) {
        
        _log.info("I'm the default controller");
        map.addAttribute("messageFromController", "Welcome Spring from Netbeans!!");
        return "index";
        
    }   
      
}

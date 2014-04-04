/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gian.message;

import java.util.HashMap;
import java.util.Map;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

/**
 *
 * @author Giuseppe Vecchione
 */
public class JsonResponse {
    
    private final String[] args;
    
    public JsonResponse(String... args){
        this.args = args;
        
    }
    
     public ModelAndView asModelAndView() {
        MappingJacksonJsonView jsonView = new MappingJacksonJsonView();
        Map<String, String> transactions = new HashMap();
        for(int i=0; i<args.length; i+=2){
            transactions.put(args[i], args[i+1]);
        }
        return new ModelAndView(jsonView, transactions);
    }
}

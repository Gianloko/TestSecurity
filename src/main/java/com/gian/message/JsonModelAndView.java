/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gian.message;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

/**
 *
 * @author fabrizio
 */
public class JsonModelAndView extends ModelAndView {

    public JsonModelAndView() {
        super(new MappingJacksonJsonView());
    }

}

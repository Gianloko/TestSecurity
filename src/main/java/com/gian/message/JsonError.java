package com.gian.message;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

public class JsonError {

    private final String message;

    public JsonError(String message) {
        this.message = message;
    }

    public ModelAndView asModelAndView() {
        MappingJacksonJsonView jsonView = new MappingJacksonJsonView();
        Map<String, String> errors = new HashMap();
        errors.put("error", message);
        return new ModelAndView(jsonView, errors);
    }
}
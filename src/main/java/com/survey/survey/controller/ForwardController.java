package com.survey.survey.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ForwardController {
    @RequestMapping(value = "/{path:[^\\.]*}")
    public String redirect() {
        // Redirige a index.html
        return "forward:/index.html";
    }
}
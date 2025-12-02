package com.amusementpark.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {
    
    @GetMapping("/")
    public String home() {
        return "redirect:/attractions";
    }
    
    @GetMapping("/test")
    @ResponseBody
    public String test() {
        return "Application is running!";
    }
}


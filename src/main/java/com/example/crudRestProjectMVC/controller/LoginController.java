package com.example.crudRestProjectMVC.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/showMyLoginPage")
    public String showMyLofinPage(){
        return "fancy-login";
    }

    // add a handler for access-denied route
    @GetMapping("/access-denied")
    public String showAccessDeniedPage(){
        return "access-denied-page";
    }
}

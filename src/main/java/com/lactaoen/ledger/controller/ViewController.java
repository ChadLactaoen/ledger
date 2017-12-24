package com.lactaoen.ledger.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class ViewController {

    @RequestMapping(value = "home", method = RequestMethod.GET)
    public String getHome() {
        return "home";
    }
}

package com.lactaoen.ledger.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class ViewController {

    @RequestMapping(method = RequestMethod.GET)
    public String getHome() {
        return "home";
    }

    @RequestMapping(value = "/transaction", method = RequestMethod.GET)
    public String getTransactionView() {
        return "transaction";
    }

    @RequestMapping(value = "/bet", method = RequestMethod.GET)
    public String getBetView() {
        return "bet";
    }
}

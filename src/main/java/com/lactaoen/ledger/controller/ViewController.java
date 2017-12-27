package com.lactaoen.ledger.controller;

import com.lactaoen.ledger.mapper.PeriodMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class ViewController {

    @Autowired
    private PeriodMapper periodMapper;

    @RequestMapping(method = RequestMethod.GET)
    public String getHome(Model model) {
        model.addAttribute("period", periodMapper.getPeriodById(1));
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

    @RequestMapping(value = "/casino", method = RequestMethod.GET)
    public String getCasinoView() {
        return "casino";
    }
}

package com.lactaoen.ledger.controller;

import com.lactaoen.ledger.mapper.PeriodMapper;
import com.lactaoen.ledger.model.Period;
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
    public String getPeriodDashboard(Model model) {
        Period period = periodMapper.getCurrentPeriod();

        if (period == null) {
            period = periodMapper.getPeriodById(periodMapper.getLastPeriodId());
            model.addAttribute("msgClass", "danger");
            model.addAttribute("msg", "You are viewing an out-of-date period. Please create a new period as soon as possible.");
        }

        model.addAttribute("period", period);
        return "dashboard/period";
    }

    @RequestMapping(value = "/year", method = RequestMethod.GET)
    public String getYearDashboard(Model model) {
        return "dashboard/year";
    }

}

package com.lactaoen.ledger.controller;

import com.lactaoen.ledger.model.Period;
import com.lactaoen.ledger.service.GraphService;
import com.lactaoen.ledger.service.PeriodService;
import com.lactaoen.ledger.util.DateConverterService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
import java.util.Calendar;

@RestController
public class DashboardController {

    private final PeriodService periodService;
    private final GraphService graphService;
    private final DateConverterService dateConverterService;

    public DashboardController(PeriodService periodService, GraphService graphService, DateConverterService dateConverterService) {
        this.periodService = periodService;
        this.graphService = graphService;
        this.dateConverterService = dateConverterService;
    }

    @GetMapping
    public ModelAndView getPeriodDashboard(@RequestParam(value = "startDate", required = false) String startDate) throws ParseException {
        Period period;
        ModelAndView mav = new ModelAndView("dashboard");

        if (startDate != null) {
            period = periodService.getPeriodByDate(startDate);
        } else {
            period = periodService.getLatestPeriod();
        }

        mav.addObject("period", period);
        mav.addObject("periodDates", periodService.getPeriodDates());
        mav.addObject("graphData", graphService.getGraphData(period));

        return mav;
    }

    @GetMapping("year")
    public ModelAndView getYearDashboard(@RequestParam(name = "year", required = false) Integer year) {
        if (year == null) {
            year = Calendar.getInstance().get(Calendar.YEAR);
        }

        Period period = periodService.getPeriodByYear(year);

        ModelAndView mav = new ModelAndView("year");
        mav.addObject("year", year);
        mav.addObject("yearsList", dateConverterService.getYearsSinceStart());
        mav.addObject("period", period);
        mav.addObject("graphData", graphService.getGraphData(period));
        return mav;
    }
}

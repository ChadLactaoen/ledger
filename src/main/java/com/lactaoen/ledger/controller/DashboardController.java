package com.lactaoen.ledger.controller;

import com.lactaoen.ledger.model.Period;
import com.lactaoen.ledger.model.Transaction;
import com.lactaoen.ledger.model.TransactionSummary;
import com.lactaoen.ledger.service.GraphService;
import com.lactaoen.ledger.service.PeriodService;
import com.lactaoen.ledger.util.DateConverterService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static com.google.common.collect.ImmutableList.toImmutableList;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.groupingBy;

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

        Period parentPeriod = new Period();
        parentPeriod.setAllocations(period.getAllocationsByParentCategory());
        parentPeriod.setTotal(period.getTotal());
        parentPeriod.setTransactions(period.getTransactions());
        mav.addObject("parentPeriod", parentPeriod);

        return mav;
    }

    @GetMapping("year")
    public ModelAndView getYearDashboard(@RequestParam(name = "year", required = false) Integer year) {
        if (year == null) {
            year = Calendar.getInstance().get(Calendar.YEAR);
        }

        Period period = periodService.getPeriodByYear(year);
        List<TransactionSummary> transactionSummary = period
                .getTransactions()
                .stream()
                .collect(groupingBy(Transaction::getName, groupingBy(transaction -> transaction.getSubcategory() == null ? transaction.getCategory().getName() : transaction.getSubcategory().getName())))
                .values()
                .stream()
                .map(Map::values)
                .flatMap(Collection::stream)
                .map(TransactionSummary::new)
                .sorted(comparing(TransactionSummary::getPrice).reversed())
                .collect(toImmutableList());

        ModelAndView mav = new ModelAndView("year");
        mav.addObject("year", year);
        mav.addObject("yearsList", dateConverterService.getYearsSinceStart());
        mav.addObject("period", period);
        mav.addObject("graphData", graphService.getGraphData(period));
        mav.addObject("transactionSummary", transactionSummary);

        Period parentPeriod = new Period();
        parentPeriod.setAllocations(period.getAllocationsByParentCategory());
        parentPeriod.setTotal(period.getTotal());
        parentPeriod.setTransactions(period.getTransactions());
        mav.addObject("parentPeriod", parentPeriod);
        return mav;
    }
}

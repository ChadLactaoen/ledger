package com.lactaoen.ledger.controller.api;

import com.lactaoen.ledger.mapper.DashboardMapper;
import com.lactaoen.ledger.model.dashboard.CategoryExpenseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private DashboardMapper dashboardMapper;

    @RequestMapping(value = "/category", method = RequestMethod.GET)
    public List<CategoryExpenseMapper> getCategoryExpensesFromCurrentYear() {
        return getCategoryExpensesByYear(Calendar.getInstance().get(Calendar.YEAR));
    }

    @RequestMapping(value = "/category/{year}", method = RequestMethod.GET)
    public List<CategoryExpenseMapper> getCategoryExpensesByYear(@PathVariable("year") Integer year) {
        return dashboardMapper.getCategoryExpensesByYear(year);
    }

    @Autowired
    public void setDashboardMapper(DashboardMapper dashboardMapper) {
        this.dashboardMapper = dashboardMapper;
    }
}

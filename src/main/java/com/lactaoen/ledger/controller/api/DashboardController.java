package com.lactaoen.ledger.controller.api;

import com.lactaoen.ledger.mapper.DashboardMapper;
import com.lactaoen.ledger.model.dashboard.CategoryExpenseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController extends AbstractApiController {

    private DashboardMapper dashboardMapper;

    @RequestMapping(value = "/category/{year}", method = RequestMethod.GET)
    public List<CategoryExpenseMapper> getCategoryExpensesByYear(@PathVariable("year") Integer year) {
        return dashboardMapper.getCategoryExpensesByYear(year);
    }

    @RequestMapping(value = "/period/{periodId}", method = RequestMethod.GET)
    public List<Map<String, BigDecimal>> getParentCategorySpendingByPeriodId(@PathVariable("periodId") Integer periodId) {
        return dashboardMapper.getParentCategorySpendingByPeriodId(periodId);
    }

    @RequestMapping(value = "/year", method = RequestMethod.GET)
    public List<Map<String, BigDecimal>> getParentCategorySpendingByYear(@RequestParam(name = "year", required = false) Integer year) {
        if (year == null) {
            year = Calendar.getInstance().get(Calendar.YEAR);
        }
        return dashboardMapper.getParentCategorySpendingByYear(year);
    }

    @Autowired
    public void setDashboardMapper(DashboardMapper dashboardMapper) {
        this.dashboardMapper = dashboardMapper;
    }
}

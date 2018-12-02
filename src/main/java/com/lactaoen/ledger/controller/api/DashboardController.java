package com.lactaoen.ledger.controller.api;

import com.lactaoen.ledger.mapper.DashboardMapper;
import com.lactaoen.ledger.model.dashboard.CategoryExpenseMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController extends AbstractApiController {

    private final DashboardMapper dashboardMapper;

    public DashboardController(DashboardMapper dashboardMapper) {
        this.dashboardMapper = dashboardMapper;
    }

    @GetMapping("/category/{year}")
    public List<CategoryExpenseMapper> getCategoryExpensesByYear(@PathVariable("year") Integer year) {
        return dashboardMapper.getCategoryExpensesByYear(year);
    }

    @GetMapping("/period/{periodId}")
    public List<Map<String, BigDecimal>> getParentCategorySpendingByPeriodId(@PathVariable("periodId") Integer periodId) {
        return dashboardMapper.getParentCategorySpendingByPeriodId(periodId);
    }

    @GetMapping("/year")
    public List<Map<String, BigDecimal>> getParentCategorySpendingByYear(@RequestParam(name = "year", required = false) Integer year) {
        if (year == null) {
            year = Calendar.getInstance().get(Calendar.YEAR);
        }
        return dashboardMapper.getParentCategorySpendingByYear(year);
    }
}

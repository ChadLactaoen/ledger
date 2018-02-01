package com.lactaoen.ledger.controller;

import com.lactaoen.ledger.mapper.BetMapper;
import com.lactaoen.ledger.mapper.DashboardMapper;
import com.lactaoen.ledger.mapper.PeriodMapper;
import com.lactaoen.ledger.mapper.TransactionMapper;
import com.lactaoen.ledger.model.Period;
import com.lactaoen.ledger.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Calendar;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Controller
@RequestMapping("/")
public class ViewController {

    @Autowired
    private BetMapper betMapper;

    @Autowired
    private DashboardMapper dashboardMapper;

    @Autowired
    private PeriodMapper periodMapper;

    @Autowired
    private TransactionMapper transactionMapper;

    @RequestMapping(method = RequestMethod.GET)
    public String getPeriodDashboard(@RequestParam(value = "periodId", required = false) Integer periodId, Model model) {
        Period period;

        if (periodId != null) {
            period = periodMapper.getPeriodById(periodId);
        } else {
            period = periodMapper.getCurrentPeriod();
        }

        if (period == null) {
            period = periodMapper.getPeriodById(periodMapper.getLastPeriodId());
            model.addAttribute("msgClass", "danger");
            model.addAttribute("msg", "You are viewing an out-of-date period. Please create a new period as soon as possible.");
        }

        model.addAttribute("period", period);
        model.addAttribute("periodMap", periodMapper.getAllPeriodsLight());
        return "dashboard/period";
    }

    @RequestMapping(value = "/gambling", method = RequestMethod.GET)
    public String getGamblingDashboard(@RequestParam(name = "year", required = false) Integer year, Model model) {
        if (year == null) {
            year = Calendar.getInstance().get(Calendar.YEAR);
        }

        model.addAttribute("year", year);
        model.addAttribute("gameGambling", dashboardMapper.getGameGamblingByYear(year));
        return "dashboard/gambling";
    }

    @RequestMapping(value = "/sports", method = RequestMethod.GET)
    public String getSportsBettingDashboard(@RequestParam(name = "year", required = false) Integer year, Model model) {
        if (year == null) {
            year = Calendar.getInstance().get(Calendar.YEAR);
        }

        model.addAttribute("year", year);
        model.addAttribute("unresolved", betMapper.getUnresolvedBets().stream().filter(bet -> bet.getSportsBet() != null).collect(toList()));
        model.addAttribute("games", dashboardMapper.getGameGamblingByYearAndParentName(year, "Sports Betting"));

        return "dashboard/sports";
    }

    @RequestMapping(value = "/poker", method = RequestMethod.GET)
    public String getPokerBettingDashboard(@RequestParam(name = "year", required = false) Integer year, Model model) {
        if (year == null) {
            year = Calendar.getInstance().get(Calendar.YEAR);
        }

        model.addAttribute("year", year);
        model.addAttribute("games", dashboardMapper.getGameGamblingByYearAndParentName(year, "Poker"));

        return "dashboard/poker";
    }

    @RequestMapping(value = "/year", method = RequestMethod.GET)
    public String getYearDashboard(@RequestParam(name = "year", required = false) Integer year, Model model) {
        if (year == null) {
            year = Calendar.getInstance().get(Calendar.YEAR);
        }

        List<Transaction> transactions = transactionMapper.getTransactionsByYear(year);

        model.addAttribute("categories", dashboardMapper.getCategoryExpensesByYear(year));
        model.addAttribute("transactions", transactions);
        model.addAttribute("year", year);
        model.addAttribute("totalSpent", transactions.stream().mapToDouble(Transaction::getPrice).sum());
        return "dashboard/year";
    }

}

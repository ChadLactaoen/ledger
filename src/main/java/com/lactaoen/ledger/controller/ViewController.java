package com.lactaoen.ledger.controller;

import com.lactaoen.ledger.mapper.BetMapper;
import com.lactaoen.ledger.mapper.DashboardMapper;
import com.lactaoen.ledger.mapper.PeriodMapper;
import com.lactaoen.ledger.mapper.TransactionMapper;
import com.lactaoen.ledger.model.Bet;
import com.lactaoen.ledger.model.Period;
import com.lactaoen.ledger.model.Transaction;
import com.lactaoen.ledger.model.dashboard.GameGamblingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

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

        List<GameGamblingMapper> gameGambling = dashboardMapper.getGameGamblingByYear(year);
        gameGambling.add(getGameGamblingMapperTotal(gameGambling));

        model.addAttribute("year", year);
        model.addAttribute("gameGambling", gameGambling);
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

        List<GameGamblingMapper> gameGamblingMap = dashboardMapper.getGameGamblingByYearAndParentName(year, "Poker");
        List<GameGamblingMapper> casinoGamblingMap = dashboardMapper.getCasinoGamblingByYearAndParentName(year, "Poker");
        List<Bet> bets = betMapper.getAllBetsByYear(year);
        List<Bet> pokerBets = bets.stream().filter(filterPokerBets()).sorted(Comparator.comparing(Bet::getDate).reversed()).collect(toList());

        model.addAttribute("year", year);
        model.addAttribute("roi", getRoi(gameGamblingMap));
        model.addAttribute("bets", pokerBets);
        model.addAttribute("games", gameGamblingMap);
        model.addAttribute("casinos", casinoGamblingMap);

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

    private GameGamblingMapper getGameGamblingMapperTotal(List<GameGamblingMapper> mapperList) {
        GameGamblingMapper total = new GameGamblingMapper();

        Double wagered = 0D;
        Double profit = 0D;
        Integer sessions = 0;
        Integer wins = 0;
        Integer ties = 0;

        for (GameGamblingMapper map : mapperList) {
            wagered += map.getWagered();
            profit += map.getProfit();
            sessions += map.getSessions();
            wins += map.getWins();
            ties += map.getTies();
        }

        total.setWagered(wagered);
        total.setProfit(profit);
        total.setSessions(sessions);
        total.setWins(wins);
        total.setTies(ties);
        total.setName("Total");
        return total;
    }

    private Double getRoi(List<GameGamblingMapper> gameGamblingMap) {
        Double wagered = gameGamblingMap.stream().mapToDouble(GameGamblingMapper::getWagered).sum();
        Double profit = gameGamblingMap.stream().mapToDouble(GameGamblingMapper::getProfit).sum();
        return profit / wagered;
    }

    private Predicate<Bet> filterPokerBets() {
        return bet -> bet.getGame().getParentGame() != null && bet.getGame().getParentGame().getName().equals("Poker");
    }

}

package com.lactaoen.ledger.controller;

import com.lactaoen.ledger.mapper.BetMapper;
import com.lactaoen.ledger.mapper.DashboardMapper;
import com.lactaoen.ledger.mapper.PeriodMapper;
import com.lactaoen.ledger.mapper.TransactionMapper;
import com.lactaoen.ledger.model.Allocation;
import com.lactaoen.ledger.model.Bet;
import com.lactaoen.ledger.model.Period;
import com.lactaoen.ledger.model.Transaction;
import com.lactaoen.ledger.model.dashboard.GameGamblingMapper;
import com.lactaoen.ledger.service.TransactionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toList;

@Controller
@RequestMapping("/")
public class ViewController {

    private static final Comparator<Allocation> BY_PARENT_CATEGORY = Comparator.comparing(a -> a.getCategory().getParentCategory().getName());
    private static final Comparator<Allocation> BY_CATEGORY = Comparator.comparing(a -> a.getCategory().getName());

    private final BetMapper betMapper;
    private final DashboardMapper dashboardMapper;
    private final PeriodMapper periodMapper;
    private final TransactionMapper transactionMapper;
    private final TransactionService transactionService;

    public ViewController(BetMapper betMapper, DashboardMapper dashboardMapper, PeriodMapper periodMapper, TransactionMapper transactionMapper, TransactionService transactionService) {
        this.betMapper = betMapper;
        this.dashboardMapper = dashboardMapper;
        this.periodMapper = periodMapper;
        this.transactionMapper = transactionMapper;
        this.transactionService = transactionService;
    }

    @ResponseBody
    @GetMapping("/heartbeat")
    public String heartbeat() {
        return "App is up";
    }

    @GetMapping
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

        List<Allocation> sortedAllocations = period.getAllocationList().stream().sorted(BY_PARENT_CATEGORY.thenComparing(BY_CATEGORY)).collect(toList());
        period.setAllocationList(sortedAllocations);

        model.addAttribute("period", period);
        model.addAttribute("periodMap", periodMapper.getAllPeriodsLight());
        return "dashboard/period";
    }

    @GetMapping("/gambling")
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

    @GetMapping(value = "/sports")
    public String getSportsBettingDashboard(@RequestParam(name = "year", required = false) Integer year, Model model) {
        if (year == null) {
            year = Calendar.getInstance().get(Calendar.YEAR);
        }

        model.addAttribute("year", year);
        model.addAttribute("unresolved", betMapper.getUnresolvedBets().stream().filter(bet -> bet.getSportsBet() != null).collect(toList()));
        model.addAttribute("games", dashboardMapper.getGameGamblingByYearAndParentName(year, "Sports Betting"));

        return "dashboard/sports";
    }

    @GetMapping("/poker")
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

    @GetMapping("/year")
    public String getYearDashboard(@RequestParam(name = "year", required = false) Integer year, Model model) {
        if (year == null) {
            year = Calendar.getInstance().get(Calendar.YEAR);
        }

        List<Transaction> transactions = transactionMapper.getTransactionsByYear(year);

        model.addAttribute("categories", transactionService.groupTransactionsByCategory(transactions));
        model.addAttribute("transactions", transactions);
        model.addAttribute("year", year);
        model.addAttribute("totalSpent", transactions.stream().filter(transaction -> transaction.isReimbursement() || (transaction.getPrice() > 0 && !transaction.isReimbursement())).mapToDouble(transaction -> Math.abs(transaction.getPrice())).sum());
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

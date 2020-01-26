package com.lactaoen.ledger.controller;

import com.lactaoen.ledger.model.Bet;
import com.lactaoen.ledger.model.data.GamblingChartEntry;
import com.lactaoen.ledger.service.BetService;
import com.lactaoen.ledger.service.data.GamblingChartService;
import com.lactaoen.ledger.service.data.GamblingGraphService;
import com.lactaoen.ledger.util.DateConverterService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Calendar;
import java.util.List;
import java.util.function.Predicate;

import static com.google.common.collect.ImmutableList.toImmutableList;

@RestController
public class GamblingController {

    private static final Predicate<Bet> SPORTS_BET_FILTER = bet -> bet.getGame().getParent().equals("Sports Betting");
    private static final Predicate<Bet> POKER_FILTER = bet -> bet.getGame().getParent().equals("Poker");

    private final BetService betService;
    private final GamblingChartService gamblingChartService;
    private final GamblingGraphService gamblingGraphService;
    private final DateConverterService dateConverterService;

    public GamblingController(BetService betService,
                              GamblingChartService gamblingChartService,
                              GamblingGraphService gamblingGraphService,
                              DateConverterService dateConverterService) {
        this.betService = betService;
        this.gamblingChartService = gamblingChartService;
        this.gamblingGraphService = gamblingGraphService;
        this.dateConverterService = dateConverterService;
    }

    @GetMapping("gambling")
    public ModelAndView getGamblingDashboard(@RequestParam(name = "year", required = false) Integer year) {
        if (year == null) {
            year = Calendar.getInstance().get(Calendar.YEAR);
        }

        List<Bet> bets = betService.getBetsByYear(year);
        List<Bet> sportsBets = bets.stream().filter(SPORTS_BET_FILTER).collect(toImmutableList());
        List<Bet> pokerBets = bets.stream().filter(POKER_FILTER).collect(toImmutableList());
        List<GamblingChartEntry> overallEntries = gamblingChartService.createChart(bets, "None");

        ModelAndView mav = new ModelAndView("gambling");
        mav.addObject("year", year);
        mav.addObject("yearsList", dateConverterService.getYearsSinceStart());
        mav.addObject("openBets", bets.stream().filter(bet -> bet.getProfit() == null).collect(toImmutableList()));
        mav.addObject("overallEntries", overallEntries);
        mav.addObject("totalEntry", gamblingChartService.createTotalEntry(overallEntries));
        mav.addObject("sportsBettingEntries", gamblingChartService.createChart(sportsBets, "Sports Betting"));
        mav.addObject("pokerEntries", gamblingChartService.createChart(pokerBets, "Poker"));
        mav.addObject("weekData", gamblingGraphService.getGamblingGraphDataByWeek(bets, year));
        mav.addObject("monthData", gamblingGraphService.getGamblingGraphDataByMonth(bets, year));
        return mav;
    }
}

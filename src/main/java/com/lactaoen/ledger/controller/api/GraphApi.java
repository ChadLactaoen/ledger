package com.lactaoen.ledger.controller.api;

import com.lactaoen.ledger.mapper.BetMapper;
import com.lactaoen.ledger.model.GraphCoordinate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

@RestController
@RequestMapping(value = "/api/graph")
public class GraphApi {

    private static final Integer WEEKS_IN_A_YEAR = 52;
    private static final Integer MONTHS_IN_A_YEAR = 12;

    private static final String[] MONTHS = new String[] {
            "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
    };

    private BetMapper betMapper;

    @SuppressWarnings("all")
    @RequestMapping(value = "/gambling/week")
    public Map<String,Object> getGamblingGraphByWeekForYear(@RequestParam(name = "year", required = false) Integer year) {
        Integer currentYear = Calendar.getInstance().get(Calendar.YEAR);

        if (year == null) {
            year = currentYear;
        }

        Integer weeksCount = year == currentYear ? betMapper.getCurrentWeekOfYear() : WEEKS_IN_A_YEAR;
        Map<Integer, BigDecimal> data = betMapper.getBetDataPointsPerWeekByYear(year).stream()
                .collect(toMap(GraphCoordinate::getX, GraphCoordinate::getY));
        List<BigDecimal> pokerData = getWeekProfitsByGameName(year, weeksCount, "Poker");
        List<BigDecimal> sportsBetData = getWeekProfitsByGameName(year, weeksCount, "Sports Betting");
        List<BigDecimal> overallData = getOverallRunningTotal(data, weeksCount);

        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("labels", getWeekLabels(weeksCount));
        returnMap.put("pokerData", pokerData);
        returnMap.put("sportsBetData", sportsBetData);
        returnMap.put("otherData", calculateOtherData(data, weeksCount, pokerData, sportsBetData));
        returnMap.put("overallData", overallData);

        return returnMap;
    }

    @SuppressWarnings("all")
    @RequestMapping(value = "/gambling/month")
    public Map<String,Object> getGamblingGraphByMonthForYear(@RequestParam(name = "year", required = false) Integer year) {
        Integer currentYear = Calendar.getInstance().get(Calendar.YEAR);

        if (year == null) {
            year = currentYear;
        }

        Integer monthCount = year == currentYear ? betMapper.getCurrentMonthOfYear() : MONTHS_IN_A_YEAR;
        Map<Integer, BigDecimal> data = betMapper.getBetDataPointsPerMonthByYear(year).stream()
                .collect(toMap(GraphCoordinate::getX, GraphCoordinate::getY));
        List<BigDecimal> pokerData = getMonthProfitsByGameName(year, monthCount, "Poker");
        List<BigDecimal> sportsBetData = getMonthProfitsByGameName(year, monthCount, "Sports Betting");
        List<BigDecimal> overallData = getOverallRunningTotal(data, monthCount);

        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("labels", getMonthLabels(monthCount));
        returnMap.put("pokerData", pokerData);
        returnMap.put("sportsBetData", sportsBetData);
        returnMap.put("otherData", calculateOtherData(data, monthCount, pokerData, sportsBetData));
        returnMap.put("overallData", overallData);

        return returnMap;
    }

    private List<BigDecimal> getOverallRunningTotal(Map<Integer, BigDecimal> data, Integer unitCount) {
        List<BigDecimal> runningProfits = new ArrayList<>();
        BigDecimal runningTotal = BigDecimal.ZERO;

        for (int i = 1; i <= unitCount; i++) {
            BigDecimal weekProfit = data.getOrDefault(i, BigDecimal.ZERO);
            runningTotal = runningTotal.add(weekProfit).setScale(2, RoundingMode.HALF_UP);
            runningProfits.add(runningTotal);
        }

        return runningProfits;
    }

    private List<BigDecimal> calculateOtherData(Map<Integer, BigDecimal> data, Integer unitCount, List<BigDecimal> pokerData, List<BigDecimal> sportsBetData) {
        List<BigDecimal> overallData = IntStream.range(1, unitCount + 1).mapToObj(i -> data.getOrDefault(i, BigDecimal.ZERO)).collect(toList());

        return IntStream.range(0, overallData.size())
                .mapToObj(i -> overallData.get(i).subtract(pokerData.get(i)).subtract(sportsBetData.get(i)).setScale(2, RoundingMode.HALF_UP))
                .collect(toList());
    }

    private List<String> getWeekLabels(Integer weeksCount) {
        return IntStream.range(1, weeksCount + 1).mapToObj(i -> "Week " + i).collect(toList());
    }

    private List<String> getMonthLabels(Integer monthsCount) {
        return IntStream.range(1, monthsCount + 1).mapToObj(i -> MONTHS[i - 1]).collect(toList());
    }

    private List<BigDecimal> getWeekProfitsByGameName(Integer year, Integer weeksCount, String name) {
        Map<Integer, BigDecimal> gameData = betMapper.getBetDataPointsPerWeekByYearAndGame(year, name).stream()
                .collect(toMap(GraphCoordinate::getX, GraphCoordinate::getY));

        return IntStream.range(1, weeksCount + 1).mapToObj(i -> gameData.getOrDefault(i, BigDecimal.ZERO)).collect(toList());
    }

    private List<BigDecimal> getMonthProfitsByGameName(Integer year, Integer monthCount, String name) {
        Map<Integer, BigDecimal> gameData = betMapper.getBetDataPointsPerMonthByYearAndGame(year, name).stream()
                .collect(toMap(GraphCoordinate::getX, GraphCoordinate::getY));

        return IntStream.range(1, monthCount + 1).mapToObj(i -> gameData.getOrDefault(i, BigDecimal.ZERO)).collect(toList());
    }

    @Autowired
    public void setBetMapper(BetMapper betMapper) {
        this.betMapper = betMapper;
    }
}

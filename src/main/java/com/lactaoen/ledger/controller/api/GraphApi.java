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

    private BetMapper betMapper;

    @SuppressWarnings("all")
    @RequestMapping(value = "/gambling")
    public Map<String,Object> getGamblingGraphForYear(@RequestParam(name = "year", required = false) Integer year) {
        Integer currentYear = Calendar.getInstance().get(Calendar.YEAR);

        if (year == null) {
            year = currentYear;
        }

        Integer weeksCount = year == currentYear ? betMapper.getCurrentWeekOfYear() : WEEKS_IN_A_YEAR;
        Map<Integer, BigDecimal> data = betMapper.getBetDataPointsByYear(year).stream()
                .collect(toMap(GraphCoordinate::getX, GraphCoordinate::getY));
        List<BigDecimal> pokerData = getWeekProfitsByGameName(year, weeksCount, "Poker");
        List<BigDecimal> sportsBetData = getWeekProfitsByGameName(year, weeksCount, "Sports Betting");
        List<BigDecimal> overallData = getOverallRunningTotal(data, weeksCount);

        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("labels", getLabels(weeksCount));
        returnMap.put("pokerData", pokerData);
        returnMap.put("sportsBetData", sportsBetData);
        returnMap.put("otherData", calculateOtherData(data, weeksCount, pokerData, sportsBetData));
        returnMap.put("overallData", overallData);

        return returnMap;
    }

    private List<BigDecimal> getOverallRunningTotal(Map<Integer, BigDecimal> data, Integer weeksCount) {
        List<BigDecimal> runningProfits = new ArrayList<>();
        BigDecimal runningTotal = BigDecimal.ZERO;

        for (int i = 1; i <= weeksCount; i++) {
            BigDecimal weekProfit = data.getOrDefault(i, BigDecimal.ZERO);
            runningTotal = runningTotal.add(weekProfit).setScale(2, RoundingMode.HALF_UP);
            runningProfits.add(runningTotal);
        }

        return runningProfits;
    }

    private List<BigDecimal> calculateOtherData(Map<Integer, BigDecimal> data, Integer weeksCount, List<BigDecimal> pokerData, List<BigDecimal> sportsBetData) {
        List<BigDecimal> overallData = IntStream.range(1, weeksCount + 1).mapToObj(i -> data.getOrDefault(i, BigDecimal.ZERO)).collect(toList());

        return IntStream.range(0, overallData.size())
                .mapToObj(i -> overallData.get(i).subtract(pokerData.get(i)).subtract(sportsBetData.get(i)).setScale(2, RoundingMode.HALF_UP))
                .collect(toList());
    }

    private List<String> getLabels(Integer weeksCount) {
        return IntStream.range(1, weeksCount + 1).mapToObj(i -> "Week " + i).collect(toList());
    }

    private List<BigDecimal> getWeekProfitsByGameName(Integer year, Integer weeksCount, String name) {
        Map<Integer, BigDecimal> gameData = betMapper.getBetDataPointsByYearAndGame(year, name).stream()
                .collect(toMap(GraphCoordinate::getX, GraphCoordinate::getY));

        return IntStream.range(1, weeksCount + 1).mapToObj(i -> gameData.getOrDefault(i, BigDecimal.ZERO)).collect(toList());
    }

    @Autowired
    public void setBetMapper(BetMapper betMapper) {
        this.betMapper = betMapper;
    }
}

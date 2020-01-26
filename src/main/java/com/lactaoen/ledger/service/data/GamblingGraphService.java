package com.lactaoen.ledger.service.data;

import com.google.common.collect.ImmutableList;
import com.lactaoen.ledger.model.Bet;
import com.lactaoen.ledger.model.data.GamblingChartDataPoints;
import com.lactaoen.ledger.model.data.GamblingPeriodGraphData;
import com.lactaoen.ledger.util.DateConverterService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Year;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingDouble;

@Service
public class GamblingGraphService {

    private static final List<String> REQUIRED_KEYS = ImmutableList.of("Poker", "Sports Betting", "Other");
    private static final int WEEKS_IN_A_YEAR = 52;
    private static final int MONTHS_IN_A_YEAR = 12;

    private final DateConverterService dateConverterService;

    public GamblingGraphService(DateConverterService dateConverterService) {
        this.dateConverterService = dateConverterService;
    }

    public GamblingChartDataPoints getGamblingGraphDataByWeek(List<Bet> bets, int year) {
        Map<Integer, List<Bet>> betsByWeek = bets.stream().collect(groupingBy(this::getWeek));
        int currentWeek = isCurrentYear(year) ? dateConverterService.getWeekOfYearByDate(dateConverterService.formatToDynamoDate(new Date())) : WEEKS_IN_A_YEAR;
        return compileGraphDataList(currentWeek, betsByWeek);
    }

    public GamblingChartDataPoints getGamblingGraphDataByMonth(List<Bet> bets, int year) {
        Map<Integer, List<Bet>> betsByMonth = bets.stream().collect(groupingBy(this::getMonth));
        int currentMonth = isCurrentYear(year) ? dateConverterService.getMonthByDate(dateConverterService.formatToDynamoDate(new Date())) : MONTHS_IN_A_YEAR;
        return compileGraphDataList(currentMonth, betsByMonth);
    }

    private int getWeek(Bet bet) {
        Date date = dateConverterService.stringToDate(bet.getDate());

        // To make weeks span from Monday to Sunday, rather than Sunday to Saturday
        LocalDate dateMinusOneDay = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().minusDays(1);
        String convertedDate = dateConverterService.formatToDynamoDate(Date.from(dateMinusOneDay.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
        return dateConverterService.getWeekOfYearByDate(convertedDate);
    }

    private int getMonth(Bet bet) {
         return dateConverterService.getMonthByDate(bet.getDate());
    }

    private boolean isCurrentYear(int year) {
        return Year.now().getValue() == year;
    }

    private GamblingChartDataPoints compileGraphDataList(int maxCount, Map<Integer, List<Bet>> betMap) {
        ImmutableList.Builder<GamblingPeriodGraphData> dataBuilder = new ImmutableList.Builder<>();
        double runningTotal = 0;

        for (int i = 1; i <= maxCount; i++) {
            if (betMap.containsKey(i)) {
                List<Bet> bets = betMap.get(i);
                runningTotal += bets.stream().filter(bet -> bet.getProfit() != null).mapToDouble(Bet::getProfit).sum();
                double totalWagered = bets.stream().mapToDouble(Bet::getWager).sum();
                dataBuilder.add(createDataForPeriod(bets, runningTotal, totalWagered));
            } else {
                dataBuilder.add(createDataForPeriod(ImmutableList.of(), runningTotal, 0));
            }
        }

        return transformToDataPoints(dataBuilder.build());
    }

    private GamblingPeriodGraphData createDataForPeriod(List<Bet> bets, double runningTotal, double wagered) {
        Map<String, Double> betMap = bets.stream().filter(bet -> bet.getProfit() !=  null).collect(groupingBy(this::groupByGeneralCategory, summingDouble(Bet::getProfit)));
        return new GamblingPeriodGraphData(betMap.getOrDefault("Poker", 0D),
                                     betMap.getOrDefault("Sports Betting", 0D),
                                     betMap.getOrDefault("Other", 0D),
                                     runningTotal,
                                     wagered);
    }

    private String groupByGeneralCategory(Bet bet) {
        String parentName = bet.getGame().getParent();
        return parentName.equals("None") ? "Other" : parentName;
    }

    private GamblingChartDataPoints transformToDataPoints(List<GamblingPeriodGraphData> graphData) {
        ImmutableList.Builder<Double> wagerDataPoints = new ImmutableList.Builder<>();
        ImmutableList.Builder<Double> profitDataPoints = new ImmutableList.Builder<>();
        ImmutableList.Builder<Double> pokerDataPoints = new ImmutableList.Builder<>();
        ImmutableList.Builder<Double> sportsBettingDataPoints = new ImmutableList.Builder<>();
        ImmutableList.Builder<Double> otherDataPoints = new ImmutableList.Builder<>();

        for (GamblingPeriodGraphData data : graphData) {
            wagerDataPoints.add(data.getWagered());
            profitDataPoints.add(data.getRunningTotal());
            pokerDataPoints.add(data.getPokerProfit());
            sportsBettingDataPoints.add(data.getSportsBettingProfit());
            otherDataPoints.add(data.getOtherProfit());
        }

        return new GamblingChartDataPoints(profitDataPoints.build(),
                                           wagerDataPoints.build(),
                                           pokerDataPoints.build(),
                                           sportsBettingDataPoints.build(),
                                           otherDataPoints.build());
    }
}

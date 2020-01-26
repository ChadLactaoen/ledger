package com.lactaoen.ledger.model.data;

import java.util.List;

public class GamblingChartDataPoints {

    private final List<Double> profitData;
    private final List<Double> wagerData;
    private final List<Double> pokerData;
    private final List<Double> sportsBettingData;
    private final List<Double> otherData;

    public GamblingChartDataPoints(List<Double> profitData,
                                   List<Double> wagerData,
                                   List<Double> pokerData,
                                   List<Double> sportsBettingData,
                                   List<Double> otherData) {
        this.profitData = profitData;
        this.wagerData = wagerData;
        this.pokerData = pokerData;
        this.sportsBettingData = sportsBettingData;
        this.otherData = otherData;
    }

    public List<Double> getProfitData() {
        return profitData;
    }

    public List<Double> getWagerData() {
        return wagerData;
    }

    public List<Double> getSportsBettingData() {
        return sportsBettingData;
    }

    public List<Double> getPokerData() {
        return pokerData;
    }

    public List<Double> getOtherData() {
        return otherData;
    }
}

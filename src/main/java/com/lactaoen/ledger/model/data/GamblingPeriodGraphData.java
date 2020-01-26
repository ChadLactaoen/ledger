package com.lactaoen.ledger.model.data;

public class GamblingPeriodGraphData {

    private final Double pokerProfit;
    private final Double sportsBettingProfit;
    private final Double otherProfit;
    private final Double runningTotal;
    private final Double wagered;

    public GamblingPeriodGraphData(Double pokerProfit,
                                   Double sportsBettingProfit,
                                   Double otherProfit,
                                   Double runningTotal,
                                   Double wagered) {
        this.pokerProfit = pokerProfit;
        this.sportsBettingProfit = sportsBettingProfit;
        this.otherProfit = otherProfit;
        this.runningTotal = runningTotal;
        this.wagered = wagered;
    }

    public Double getPokerProfit() {
        return pokerProfit;
    }

    public Double getSportsBettingProfit() {
        return sportsBettingProfit;
    }

    public Double getOtherProfit() {
        return otherProfit;
    }

    public Double getRunningTotal() {
        return runningTotal;
    }

    public Double getWagered() {
        return wagered;
    }
}

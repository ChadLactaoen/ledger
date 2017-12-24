package com.lactaoen.ledger.model;

import java.sql.Date;

public class Bet {

    private int betId;
    private Date date;
    private Game game;
    private Casino casino;
    private String memo;
    private Double wager;
    private Double profit;

    public Bet() {
    }

    public int getBetId() {
        return betId;
    }

    public void setBetId(int betId) {
        this.betId = betId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Casino getCasino() {
        return casino;
    }

    public void setCasino(Casino casino) {
        this.casino = casino;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Double getWager() {
        return wager;
    }

    public void setWager(Double wager) {
        this.wager = wager;
    }

    public Double getProfit() {
        return profit;
    }

    public void setProfit(Double profit) {
        this.profit = profit;
    }

    @Override
    public String toString() {
        return "Bet{" +
                "betId=" + betId +
                ", date=" + date +
                ", game=" + game +
                ", casino=" + casino +
                ", memo='" + memo + '\'' +
                ", wager=" + wager +
                ", profit=" + profit +
                '}';
    }
}

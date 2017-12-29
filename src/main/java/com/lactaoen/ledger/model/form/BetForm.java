package com.lactaoen.ledger.model.form;

import java.sql.Date;

public class BetForm {

    private Integer betId;
    private Date date;
    private Integer gameId;
    private Integer casinoId;
    private Double wager;
    private Double profit;
    private String memo;

    public BetForm() {
    }

    public Integer getBetId() {
        return betId;
    }

    public void setBetId(Integer betId) {
        this.betId = betId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public Integer getCasinoId() {
        return casinoId;
    }

    public void setCasinoId(Integer casinoId) {
        this.casinoId = casinoId;
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

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}

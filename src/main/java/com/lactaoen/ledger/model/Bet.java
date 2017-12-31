package com.lactaoen.ledger.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lactaoen.ledger.model.form.BetForm;

import java.sql.Date;

public class Bet {

    private Integer betId;
    private Date date;
    private Game game;
    private Casino casino;
    private String memo;
    private Double wager;
    private Double profit;

    public Bet() {
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

    @JsonIgnore
    public BetForm toBetForm() {
        BetForm form = new BetForm();
        form.setBetId(betId);
        form.setDate(date);
        form.setGameId(game.getGameId());
        form.setCasinoId(casino.getCasinoId());
        form.setMemo(memo);
        form.setWager(wager);
        form.setProfit(profit);
        return form;
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

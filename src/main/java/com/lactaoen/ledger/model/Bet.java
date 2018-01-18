package com.lactaoen.ledger.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
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
    private SportsBet sportsBet;

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

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public SportsBet getSportsBet() {
        return sportsBet;
    }

    public void setSportsBet(SportsBet sportsBet) {
        this.sportsBet = sportsBet;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getSportsBetDescriptor() {
        String descriptor = "";

        if (sportsBet != null) {

            // Odds
            if (sportsBet.getOdds() > 0) {
                descriptor = "+" + sportsBet.getOdds();
            } else {
                descriptor = sportsBet.getOdds().toString();
            }

            // Matchup
            descriptor += " " + sportsBet.getForTeam().getAbbreviation();

            // Spread/Line
            String betType = sportsBet.getBetType().getName();
            if (("Moneyline").equals(betType)) {
                descriptor += " SU";
            } else if (("Over").equals(betType) || ("Under").equals(betType)) {
                descriptor += " " + sportsBet.getLine() + betType.toLowerCase().charAt(0);
            } else {
                descriptor += " " + sportsBet.getLine();
            }

            // Game Type
//            String gameType = sportsBet.getGameType();
//            if (!("Full").equals(gameType)) {
//                descriptor +=
//            }
//
            // Is Live
            if (sportsBet.isLive()) {
                descriptor += " (Live)";
            }
            return descriptor;
        }

        return null;
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
                "sportsBet=" + sportsBet +
                ", profit=" + profit +
                ", wager=" + wager +
                ", memo='" + memo + '\'' +
                ", casino=" + casino +
                ", game=" + game +
                ", date=" + date +
                ", betId=" + betId +
                '}';
    }
}

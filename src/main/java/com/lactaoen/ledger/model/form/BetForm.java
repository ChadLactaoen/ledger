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

    /* Only for sports bets */
    private Double odds;
    private Double spread;
    private Integer forTeamId;
    private Integer againstTeamId;
    private String sportsBetType;
    private Boolean isInGame;

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

    public Double getOdds() {
        return odds;
    }

    public void setOdds(Double odds) {
        this.odds = odds;
    }

    public Double getSpread() {
        return spread;
    }

    public void setSpread(Double spread) {
        this.spread = spread;
    }

    public Integer getForTeamId() {
        return forTeamId;
    }

    public void setForTeamId(Integer forTeamId) {
        this.forTeamId = forTeamId;
    }

    public Integer getAgainstTeamId() {
        return againstTeamId;
    }

    public void setAgainstTeamId(Integer againstTeamId) {
        this.againstTeamId = againstTeamId;
    }

    public String getSportsBetType() {
        return sportsBetType;
    }

    public void setSportsBetType(String sportsBetType) {
        this.sportsBetType = sportsBetType;
    }

    public Boolean getInGame() {
        return isInGame;
    }

    public void setInGame(Boolean inGame) {
        isInGame = inGame;
    }
}

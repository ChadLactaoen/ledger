package com.lactaoen.ledger.model.form;

import com.amazonaws.util.StringUtils;
import com.lactaoen.ledger.model.Bet;

public class BetForm {

    private String betId;
    private String date;
    private String gameName;
    private String casino;
    private Double wager;
    private Double profit;
    private String memo;

    private String betType;
    private String gameType;
    private String forTeamLocation;
    private String againstTeamLocation;
    private Double line;
    private Integer odds;
    private boolean live;

    public BetForm() {
    }

    public String getBetId() {
        return betId;
    }

    public void setBetId(String betId) {
        this.betId = betId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getCasino() {
        return casino;
    }

    public void setCasino(String casino) {
        this.casino = casino;
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

    public String getBetType() {
        return betType;
    }

    public void setBetType(String betType) {
        this.betType = betType;
    }

    public String getGameType() {
        return gameType;
    }

    public void setGameType(String gameType) {
        this.gameType = gameType;
    }

    public String getForTeamLocation() {
        return forTeamLocation;
    }

    public void setForTeamLocation(String forTeamLocation) {
        this.forTeamLocation = forTeamLocation;
    }

    public String getAgainstTeamLocation() {
        return againstTeamLocation;
    }

    public void setAgainstTeamLocation(String againstTeamLocation) {
        this.againstTeamLocation = againstTeamLocation;
    }

    public Double getLine() {
        return line;
    }

    public void setLine(Double line) {
        this.line = line;
    }

    public Integer getOdds() {
        return odds;
    }

    public void setOdds(Integer odds) {
        this.odds = odds;
    }

    public boolean isLive() {
        return live;
    }

    public void setLive(boolean live) {
        this.live = live;
    }

    public Bet toBet() {
        Bet bet = new Bet();
        bet.setBetId(StringUtils.isNullOrEmpty(betId) ? bet.getBetId() : betId);
        bet.setDate(date);
        bet.setCasino(casino);
        bet.setWager(wager);
        bet.setProfit(profit);
        bet.setMemo(memo);

        bet.setBetType(betType);
        bet.setGameType(gameType);
        bet.setLine(line);
        bet.setOdds(odds);
        bet.setLive(live);
        return bet;
    }
}

package com.lactaoen.ledger.model;

public class SportsBet extends Bet {

    private Integer odds;
    private Team forTeam;
    private Team againstTeam;
    private Integer line;
    private BetType betType;
    private GameType gameType;
    private boolean isLive;

    public SportsBet() {
    }

    public Integer getOdds() {
        return odds;
    }

    public void setOdds(Integer odds) {
        this.odds = odds;
    }

    public Team getForTeam() {
        return forTeam;
    }

    public void setForTeam(Team forTeam) {
        this.forTeam = forTeam;
    }

    public Team getAgainstTeam() {
        return againstTeam;
    }

    public void setAgainstTeam(Team againstTeam) {
        this.againstTeam = againstTeam;
    }

    public Integer getLine() {
        return line;
    }

    public void setLine(Integer line) {
        this.line = line;
    }

    public BetType getBetType() {
        return betType;
    }

    public void setBetType(BetType betType) {
        this.betType = betType;
    }

    public GameType getGameType() {
        return gameType;
    }

    public void setGameType(GameType gameType) {
        this.gameType = gameType;
    }

    public boolean isLive() {
        return isLive;
    }

    public void setLive(boolean live) {
        isLive = live;
    }
}

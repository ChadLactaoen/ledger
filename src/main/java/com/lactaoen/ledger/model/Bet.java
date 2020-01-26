package com.lactaoen.ledger.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.lactaoen.ledger.model.form.BetForm;

@DynamoDBTable(tableName = "Bet")
public class Bet {

    private String betId;
    private String date;
    private Game game;
    private String casino;
    private Double wager;
    private Double profit;
    private String memo;

    private String betType;
    private String gameType;
    private Team forTeam;
    private Team againstTeam;
    private Double line;
    private Integer odds;
    private boolean live;

    public Bet() {
    }

    @DynamoDBHashKey
    @DynamoDBAutoGeneratedKey
    public String getBetId() {
        return betId;
    }

    public void setBetId(String betId) {
        this.betId = betId;
    }

    @DynamoDBAttribute
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @DynamoDBAttribute
    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    @DynamoDBAttribute
    public String getCasino() {
        return casino;
    }

    public void setCasino(String casino) {
        this.casino = casino;
    }

    @DynamoDBAttribute
    public Double getWager() {
        return wager;
    }

    public void setWager(Double wager) {
        this.wager = wager;
    }

    @DynamoDBAttribute
    public Double getProfit() {
        return profit;
    }

    public void setProfit(Double profit) {
        this.profit = profit;
    }

    @DynamoDBAttribute
    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @DynamoDBAttribute
    public String getBetType() {
        return betType;
    }

    public void setBetType(String betType) {
        this.betType = betType;
    }

    @DynamoDBAttribute
    public String getGameType() {
        return gameType;
    }

    public void setGameType(String gameType) {
        this.gameType = gameType;
    }

    @DynamoDBAttribute
    public Team getForTeam() {
        return forTeam;
    }

    public void setForTeam(Team forTeam) {
        this.forTeam = forTeam;
    }

    @DynamoDBAttribute
    public Team getAgainstTeam() {
        return againstTeam;
    }

    public void setAgainstTeam(Team againstTeam) {
        this.againstTeam = againstTeam;
    }

    @DynamoDBAttribute
    public Double getLine() {
        return line;
    }

    public void setLine(Double line) {
        this.line = line;
    }

    @DynamoDBAttribute
    public Integer getOdds() {
        return odds;
    }

    public void setOdds(Integer odds) {
        this.odds = odds;
    }

    @DynamoDBAttribute
    public boolean isLive() {
        return live;
    }

    public void setLive(boolean live) {
        this.live = live;
    }

    public BetForm toForm() {
        BetForm betForm = new BetForm();
        betForm.setBetId(betId);
        betForm.setDate(date);
        betForm.setGameName(game.getName());
        betForm.setCasino(casino);
        betForm.setWager(wager);
        betForm.setProfit(profit);
        betForm.setMemo(memo);

        betForm.setBetType(betType);
        betForm.setGameType(gameType);
        betForm.setForTeamLocation(forTeam.getLocation());
        betForm.setAgainstTeamLocation(againstTeam.getLocation());
        betForm.setLine(line);
        betForm.setOdds(odds);
        betForm.setLive(live);
        return betForm;
    }
}

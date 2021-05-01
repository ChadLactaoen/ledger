package com.lactaoen.ledger.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@DynamoDBTable(tableName = "Template")
public class Template {

    private String templateName;
    private String type;
    private String category;
    private String name;
    private Double price;
    private String game;
    private String casino;
    private Double wager;
    private Integer odds;
    private Double line;
    private String sportsBetType;
    private String gameType;

    public Template() {
    }

    @DynamoDBHashKey
    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    @DynamoDBRangeKey
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @DynamoDBAttribute
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @DynamoDBAttribute
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @DynamoDBAttribute
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @DynamoDBAttribute
    public String getGame() {
        return game;
    }

    public void setGame(String game) {
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
    public Integer getOdds() {
        return odds;
    }

    public void setOdds(Integer odds) {
        this.odds = odds;
    }

    @DynamoDBAttribute
    public Double getLine() {
        return line;
    }

    public void setLine(Double line) {
        this.line = line;
    }

    @DynamoDBAttribute
    public String getSportsBetType() {
        return sportsBetType;
    }

    public void setSportsBetType(String sportsBetType) {
        this.sportsBetType = sportsBetType;
    }

    @DynamoDBAttribute
    public String getGameType() {
        return gameType;
    }

    public void setGameType(String gameType) {
        this.gameType = gameType;
    }

    @Override
    public String toString() {
        return "Template{" +
                "templateName='" + templateName + '\'' +
                ", type='" + type + '\'' +
                ", category='" + category + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", game='" + game + '\'' +
                ", casino='" + casino + '\'' +
                ", wager=" + wager +
                ", odds=" + odds +
                ", line=" + line +
                ", sportsBetType='" + sportsBetType + '\'' +
                ", gameType='" + gameType + '\'' +
                '}';
    }
}

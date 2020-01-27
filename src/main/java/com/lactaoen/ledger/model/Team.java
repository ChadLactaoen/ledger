package com.lactaoen.ledger.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.Objects;

@DynamoDBTable(tableName = "Team")
@DynamoDBDocument
public class Team {

    private String location;
    private String mascot;
    private String game;
    private String abbreviation;

    public Team() {
    }

    @DynamoDBHashKey
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @DynamoDBRangeKey
    public String getMascot() {
        return mascot;
    }

    public void setMascot(String mascot) {
        this.mascot = mascot;
    }

    @DynamoDBAttribute
    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    @DynamoDBAttribute
    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Team)) return false;
        Team team = (Team) o;
        return Objects.equals(location, team.location) &&
                Objects.equals(mascot, team.mascot) &&
                Objects.equals(game, team.game) &&
                Objects.equals(abbreviation, team.abbreviation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(location, mascot, game, abbreviation);
    }
}

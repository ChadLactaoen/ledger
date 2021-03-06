package com.lactaoen.ledger.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.Objects;

@DynamoDBTable(tableName = "Game")
@DynamoDBDocument
public class Game {

    private String name;
    private String parent;
    private String color;

    public Game() {
    }

    @DynamoDBHashKey
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @DynamoDBAttribute
    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    @DynamoDBAttribute
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Game)) return false;
        Game game = (Game) o;
        return Objects.equals(name, game.name) &&
                Objects.equals(parent, game.parent) &&
                Objects.equals(color, game.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, parent, color);
    }

    @Override
    public String toString() {
        return "Game{" +
                "name='" + name + '\'' +
                ", parent='" + parent + '\'' +
                ", color='" + color + '\'' +
                '}';
    }
}

package com.lactaoen.ledger.model;

public class Game {

    private int gameId;
    private String name;
    private Game parentGame;

    public Game() {
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Game getParentGame() {
        return parentGame;
    }

    public void setParentGame(Game parentGame) {
        this.parentGame = parentGame;
    }

    @Override
    public String toString() {
        return "Game{" +
                "gameId=" + gameId +
                ", name='" + name + '\'' +
                ", parentGame=" + parentGame +
                '}';
    }
}

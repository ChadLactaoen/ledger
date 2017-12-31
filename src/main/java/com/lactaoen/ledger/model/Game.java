package com.lactaoen.ledger.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lactaoen.ledger.model.form.GameForm;

public class Game {

    private Integer gameId;
    private String name;
    private Game parentGame;

    public Game() {
    }

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
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

    @JsonIgnore
    public GameForm toGameForm() {
        GameForm game = new GameForm();
        game.setGameId(gameId);
        game.setName(name);
        if (parentGame != null) {
            game.setParentId(parentGame.getGameId());
        }
        return game;
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

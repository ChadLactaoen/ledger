package com.lactaoen.ledger.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lactaoen.ledger.model.form.TeamForm;

public class Team {

    private Integer teamId;
    private Game game;
    private String location;
    private String mascot;
    private String abbreviation;

    public Team() {
    }

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getMascot() {
        return mascot;
    }

    public void setMascot(String mascot) {
        this.mascot = mascot;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    @JsonIgnore
    public TeamForm toTeamForm() {
        TeamForm form = new TeamForm();
        form.setTeamId(teamId);
        form.setLocation(location);
        form.setMascot(mascot);
        form.setAbbreviation(abbreviation);

        if (game != null) {
            form.setGameId(game.getGameId());
        }

        return form;
    }
}

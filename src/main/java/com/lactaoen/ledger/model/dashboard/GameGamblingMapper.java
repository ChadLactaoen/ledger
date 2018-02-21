package com.lactaoen.ledger.model.dashboard;

public class GameGamblingMapper {

    private Integer id;
    private String name;
    private Double wagered;
    private Double profit;
    private Integer sessions;
    private Integer wins;
    private Integer ties;

    public GameGamblingMapper() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getWagered() {
        return wagered;
    }

    public void setWagered(Double wagered) {
        this.wagered = wagered;
    }

    public Double getProfit() {
        return profit;
    }

    public void setProfit(Double profit) {
        this.profit = profit;
    }

    public Integer getSessions() {
        return sessions;
    }

    public void setSessions(Integer sessions) {
        this.sessions = sessions;
    }

    public Integer getWins() {
        return wins;
    }

    public void setWins(Integer wins) {
        this.wins = wins;
    }

    public Integer getTies() {
        return ties;
    }

    public void setTies(Integer ties) {
        this.ties = ties;
    }
}

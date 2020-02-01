package com.lactaoen.ledger.model.data;

public class StatRecord {

    private int wins;
    private int losses;
    private int ties;

    public StatRecord() {
        wins = 0;
        losses = 0;
        ties = 0;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public int getTies() {
        return ties;
    }

    public void setTies(int ties) {
        this.ties = ties;
    }

    public void add(Result result) {
        switch (result) {
            case WIN:
                wins++;
                break;
            case LOSS:
                losses++;
                break;
            case TIE:
                ties++;
                break;
        }
    }
}

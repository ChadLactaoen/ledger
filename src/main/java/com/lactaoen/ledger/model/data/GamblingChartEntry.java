package com.lactaoen.ledger.model.data;

import com.lactaoen.ledger.model.Game;

public class GamblingChartEntry {

    private final Game game;
    private final int wins;
    private final int losses;
    private final int ties;
    private final double wagered;
    private final double profit;

    private GamblingChartEntry(Builder builder) {
        this.game = builder.game;
        this.wins = builder.wins;
        this.losses = builder.losses;
        this.ties = builder.ties;
        this.wagered = builder.wagered;
        this.profit = builder.profit;
    }

    public Game getGame() {
        return game;
    }

    public int getWins() {
        return wins;
    }

    public int getLosses() {
        return losses;
    }

    public int getTies() {
        return ties;
    }

    public double getWagered() {
        return wagered;
    }

    public double getProfit() {
        return profit;
    }

    public int getTotalPlayed() {
        return wins + losses + ties;
    }

    public static class Builder {

        private Game game;
        private int wins;
        private int losses;
        private int ties;
        private double wagered;
        private double profit;

        public Builder() {
        }

        public Builder setGame(Game game) {
            this.game = game;
            return this;
        }

        public Builder setWins(int wins) {
            this.wins = wins;
            return this;
        }

        public Builder setLosses(int losses) {
            this.losses = losses;
            return this;
        }

        public Builder setTies(int ties) {
            this.ties = ties;
            return this;
        }

        public Builder setWagered(double wagered) {
            this.wagered = wagered;
            return this;
        }

        public Builder setProfit(double profit) {
            this.profit = profit;
            return this;
        }

        public GamblingChartEntry build() {
            return new GamblingChartEntry(this);
        }
    }
}

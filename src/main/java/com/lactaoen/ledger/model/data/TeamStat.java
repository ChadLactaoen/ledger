package com.lactaoen.ledger.model.data;

import com.lactaoen.ledger.model.Team;

public class TeamStat {

    private final Team team;
    private final double wagered;
    private final double profit;
    private final int forBetCount;
    private final int againstBetCount;
    private final StatRecord forStats;
    private final StatRecord againstStats;
    private final StatRecord overStats;
    private final StatRecord underStats;
    private final StatRecord moneylineStats;
    private final StatRecord pointsStats;

    private TeamStat(Builder builder) {
        this.team = builder.team;
        this.wagered = builder.wagered;
        this.profit = builder.profit;
        this.forBetCount = builder.forBetCount;
        this.againstBetCount = builder.againstBetCount;
        this.forStats = builder.forStats;
        this.againstStats = builder.againstStats;
        this.overStats = builder.overStats;
        this.underStats = builder.underStats;
        this.moneylineStats = builder.moneylineStats;
        this.pointsStats = builder.pointsStats;
    }

    public Team getTeam() {
        return team;
    }

    public double getWagered() {
        return wagered;
    }

    public double getProfit() {
        return profit;
    }

    public int getForBetCount() {
        return forBetCount;
    }

    public int getAgainstBetCount() {
        return againstBetCount;
    }

    public StatRecord getForStats() {
        return forStats;
    }

    public StatRecord getAgainstStats() {
        return againstStats;
    }

    public StatRecord getOverStats() {
        return overStats;
    }

    public StatRecord getUnderStats() {
        return underStats;
    }

    public StatRecord getMoneylineStats() {
        return moneylineStats;
    }

    public StatRecord getPointsStats() {
        return pointsStats;
    }

    public static class Builder {

        private Team team;
        private double wagered;
        private double profit;
        private int forBetCount;
        private int againstBetCount;
        private StatRecord forStats;
        private StatRecord againstStats;
        private StatRecord overStats;
        private StatRecord underStats;
        private StatRecord moneylineStats;
        private StatRecord pointsStats;

        public Builder() {
        }

        public Builder withTeam(Team team) {
            this.team = team;
            return this;
        }

        public Builder withWagered(double wagered) {
            this.wagered = wagered;
            return this;
        }

        public Builder withProfit(double profit) {
            this.profit = profit;
            return this;
        }

        public Builder withForBetCount(int forBetCount) {
            this.forBetCount = forBetCount;
            return this;
        }

        public Builder withAgainstBetCount(int againstBetCount) {
            this.againstBetCount = againstBetCount;
            return this;
        }

        public Builder withForStats(StatRecord forStats) {
            this.forStats = forStats;
            return this;
        }

        public Builder withAgainstStats(StatRecord againstStats) {
            this.againstStats = againstStats;
            return this;
        }

        public Builder withOverStats(StatRecord overStats) {
            this.overStats = overStats;
            return this;
        }

        public Builder withUnderStats(StatRecord underStats) {
            this.underStats = underStats;
            return this;
        }

        public Builder withMoneylineStats(StatRecord moneylineStats) {
            this.moneylineStats = moneylineStats;
            return this;
        }

        public Builder withPointsStats(StatRecord pointsStats) {
            this.pointsStats = pointsStats;
            return this;
        }

        public TeamStat build() {
            return new TeamStat(this);
        }
    }
}

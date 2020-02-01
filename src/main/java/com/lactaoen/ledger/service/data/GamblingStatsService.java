package com.lactaoen.ledger.service.data;

import com.google.common.collect.ImmutableList;
import com.lactaoen.ledger.model.Bet;
import com.lactaoen.ledger.model.Team;
import com.lactaoen.ledger.model.data.Result;
import com.lactaoen.ledger.model.data.StatRecord;
import com.lactaoen.ledger.model.data.TeamStat;
import com.lactaoen.ledger.service.BetService;
import com.lactaoen.ledger.service.TeamService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import static com.google.common.collect.ImmutableList.toImmutableList;
import static java.util.stream.Collectors.groupingBy;

@Service
public class GamblingStatsService {

    private static final Predicate<Bet> NOT_PARLAY_AND_RESOLVED = bet -> !"Parlay".equals(bet.getBetType()) && bet.getProfit() != null;
    private static final Comparator<TeamStat> COMPARE_BY_LOCATION = Comparator.comparing(teamStat -> teamStat.getTeam().getLocation());
    private static final Comparator<TeamStat> COMPARE_BY_MASCOT = Comparator.comparing(teamStat -> teamStat.getTeam().getMascot());

    private final BetService betService;
    private final TeamService teamService;

    public GamblingStatsService(BetService betService, TeamService teamService) {
        this.betService = betService;
        this.teamService = teamService;
    }

    public List<TeamStat> getTeamStatsBySportAlTime(String sport) {
        return getTeamStats(sport, betService.getAllBets());
    }

    public List<TeamStat> getTeamStatsByYearAndSport(int year, String sport) {
       return getTeamStats(sport, betService.getBetsByYear(year));
    }

    private List<TeamStat> getTeamStats(String sport, List<Bet> bets) {
        Map<Team, List<Bet>> teamBetMap = bets.stream()
                .filter(bet -> sport.equals(bet.getGame().getName()))
                .filter(NOT_PARLAY_AND_RESOLVED)
                .collect(groupingBy(Bet::getForTeam));
        Map<Team, List<Bet>> againstTeamBetMap = teamBetMap.values().stream().flatMap(Collection::stream).collect(groupingBy(Bet::getAgainstTeam));

        List<Team> teams = teamService.getTeamsByGame(sport);

        return teams.stream()
                .map(team -> createTeamStat(team, teamBetMap, againstTeamBetMap))
                .sorted(COMPARE_BY_LOCATION.thenComparing(COMPARE_BY_MASCOT))
                .collect(toImmutableList());
    }

    private TeamStat createTeamStat(Team team, Map<Team, List<Bet>> teamBetMap, Map<Team, List<Bet>> againstTeamBetMap) {
        StatRecord forStats = new StatRecord();
        StatRecord againstStats = new StatRecord();
        StatRecord overStats = new StatRecord();
        StatRecord underStats = new StatRecord();
        StatRecord moneylineStats = new StatRecord();
        StatRecord pointsStats = new StatRecord();
        double wagered = 0;
        double profit = 0;
        int forBetCount = 0;
        int againstBetCount = 0;

        List<Bet> forBets = teamBetMap.getOrDefault(team, ImmutableList.of());

        for (Bet forBet : forBets) {
            double betProfit = forBet.getProfit();

            wagered += forBet.getWager();
            profit += betProfit;

            Result result = resolveToResult(betProfit);
            String betType = forBet.getBetType();

            forStats.add(result);
            forBetCount++;

            if ("Over".equals(betType)) {
                overStats.add(result);
            } else if ("Under".equals(betType)) {
                underStats.add(result);
            } else if ("Points".equals(betType)) {
                pointsStats.add(result);
            } else if ("Moneyline".equals(betType)) {
                moneylineStats.add(result);
            }
        }

        List<Bet> againstBets = againstTeamBetMap.getOrDefault(team, ImmutableList.of());

        for (Bet againstBet : againstBets) {
            Result result = resolveToResult(againstBet.getProfit());
            againstStats.add(result);
            againstBetCount++;

            String betType = againstBet.getBetType();

            if ("Over".equals(betType)) {
                overStats.add(result);
            } else if ("Under".equals(betType)) {
                underStats.add(result);
            }
        }

        return new TeamStat.Builder()
                .withWagered(wagered)
                .withProfit(profit)
                .withForStats(forStats)
                .withAgainstStats(againstStats)
                .withOverStats(overStats)
                .withUnderStats(underStats)
                .withMoneylineStats(moneylineStats)
                .withPointsStats(pointsStats)
                .withTeam(team)
                .withForBetCount(forBetCount)
                .withAgainstBetCount(againstBetCount)
                .build();
    }

    private Result resolveToResult(double profit) {
        return profit > 0 ? Result.WIN : profit == 0 ? Result.TIE : Result.LOSS;
    }
}

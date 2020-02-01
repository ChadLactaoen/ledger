package com.lactaoen.ledger.service;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.google.common.collect.ImmutableMap;
import com.lactaoen.ledger.model.Bet;
import com.lactaoen.ledger.model.Game;
import com.lactaoen.ledger.model.form.BetForm;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class BetService {

    private final DynamoDBMapper dynamoDBMapper;
    private final GameService gameService;
    private final TeamService teamService;

    public BetService(DynamoDBMapper dynamoDBMapper, GameService gameService, TeamService teamService) {
        this.dynamoDBMapper = dynamoDBMapper;
        this.gameService = gameService;
        this.teamService = teamService;
    }

    public Bet getBetById(String betId) {
        return dynamoDBMapper.load(Bet.class, betId);
    }

    public List<Bet> getBetsByYear(int year) {
        return dynamoDBMapper.query(Bet.class, createEffectiveYearIndexQuery(String.valueOf(year)));
    }

    public List<Bet> getAllBets() {
        return dynamoDBMapper.scan(Bet.class, new DynamoDBScanExpression());
    }

    public void saveBet(BetForm betForm) {
        Bet bet = betForm.toBet();

        Game game = gameService.getGameByName(betForm.getGameName());
        bet.setGame(game);

        if (game.getParent().equals("Sports Betting")) {
             bet.setForTeam(teamService.getTeamByLocationAndMascot(betForm.getForTeamLocation(), betForm.getForTeamMascot()));
             bet.setAgainstTeam(teamService.getTeamByLocationAndMascot(betForm.getAgainstTeamLocation(), betForm.getAgainstTeamMascot()));
        }

        dynamoDBMapper.save(bet);
    }

    public void updateBet(String betId, boolean win) {
        Bet bet = getBetById(betId);
        if (!"Sports Betting".equals(bet.getGame().getParent())) {
            return;
        }

        bet.setProfit(win ? setCalculatedProfit(bet) : bet.getWager() * -1);
        dynamoDBMapper.save(bet);
    }

    private double setCalculatedProfit(Bet bet) {
        BigDecimal odds = new BigDecimal(bet.getOdds().doubleValue());
        BigDecimal wager = new BigDecimal(bet.getWager());

        if (odds.compareTo(BigDecimal.ZERO) > 0) {
            return wager.multiply(odds.divide(new BigDecimal(100))).doubleValue();
        }

        return wager.multiply(new BigDecimal(100)).divide(odds.abs()).doubleValue();
    }

    private DynamoDBQueryExpression<Bet> createEffectiveYearIndexQuery(String year) {
        return new DynamoDBQueryExpression<Bet>()
                .withIndexName("effectiveYear-betId-index")
                .withConsistentRead(false)
                .withKeyConditionExpression("effectiveYear = :year")
                .withExpressionAttributeValues(ImmutableMap.of(":year", new AttributeValue(year)));
    }

    private DynamoDBQueryExpression<Bet> createOpenBetIndexQuery() {
        return new DynamoDBQueryExpression<Bet>()
                .withIndexName("profit-betId-index")
                .withConsistentRead(false)
                .withKeyConditionExpression("profit");
    }
}

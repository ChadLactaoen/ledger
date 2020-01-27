package com.lactaoen.ledger.service;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.google.common.collect.ImmutableMap;
import com.lactaoen.ledger.model.Team;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

import static com.google.common.collect.ImmutableList.toImmutableList;

@Service
public class TeamService {

    private static final Comparator<Team> BY_LOCATION = Comparator.comparing(Team::getLocation);

    private final DynamoDBMapper dynamoDBMapper;

    public TeamService(DynamoDBMapper dynamoDBMapper) {
        this.dynamoDBMapper = dynamoDBMapper;
    }

    public Team getTeamByLocationAndMascot(String location, String mascot) {
        return dynamoDBMapper.load(Team.class, location, mascot);
    }

    public List<Team> getTeamsByGame(String game) {
        DynamoDBScanExpression expression = new DynamoDBScanExpression()
                .withFilterExpression("#game = :game")
                .withExpressionAttributeNames(ImmutableMap.of("#game", "game"))
                .withExpressionAttributeValues(ImmutableMap.of(":game", new AttributeValue(game)));

        List<Team> teams = dynamoDBMapper.scan(Team.class, expression);
        return teams.stream().sorted(BY_LOCATION).collect(toImmutableList());
    }

    public void saveTeam(Team team) {
        dynamoDBMapper.save(team);
    }
}

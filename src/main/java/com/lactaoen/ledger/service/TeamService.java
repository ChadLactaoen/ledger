package com.lactaoen.ledger.service;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.google.common.collect.ImmutableMap;
import com.lactaoen.ledger.model.Team;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamService {

    private final DynamoDBMapper dynamoDBMapper;

    public TeamService(DynamoDBMapper dynamoDBMapper) {
        this.dynamoDBMapper = dynamoDBMapper;
    }

    public Team getTeamByLocationAndMascot(String location, String mascot) {
        return dynamoDBMapper.load(Team.class, location, mascot);
    }

    public List<Team> getTeamsByGame(String game) {
        DynamoDBQueryExpression<Team> queryExpression = new DynamoDBQueryExpression<Team>()
                .withIndexName("game-location-index")
                .withConsistentRead(false)
                .withKeyConditionExpression("game = :game")
                .withExpressionAttributeValues(ImmutableMap.of(":game", new AttributeValue(game)));

        return dynamoDBMapper.query(Team.class, queryExpression);
    }

    public void saveTeam(Team team) {
        dynamoDBMapper.save(team);
    }
}

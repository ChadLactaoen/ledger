package com.lactaoen.ledger.service;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.google.common.collect.ImmutableMap;
import com.lactaoen.ledger.model.Game;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static com.google.common.collect.ImmutableList.toImmutableList;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.groupingBy;

@Service
public class GameService {

    public static final Comparator<Game> SORT_BY_GAME_NAME = Comparator.comparing(Game::getName);
    public static final Comparator<Game> SORT_BY_PARENT_NAME = Comparator.comparing(Game::getParent).thenComparing(Game::getName);

    private final DynamoDBMapper dynamoDBMapper;

    public GameService(DynamoDBMapper dynamoDBMapper) {
        this.dynamoDBMapper = dynamoDBMapper;
    }

    public List<Game> getAllGames(Comparator<Game> sort) {
        return dynamoDBMapper.scan(Game.class, new DynamoDBScanExpression()).stream().sorted(sort).collect(toImmutableList());
    }

    public List<Game> getLowestLevelGames() {
        Map<String, List<Game>> gameMap = dynamoDBMapper.scan(Game.class, new DynamoDBScanExpression()).stream().collect(groupingBy(Game::getParent));
        return gameMap.values().stream()
                               .flatMap(Collection::stream)
                               .filter(game -> !gameMap.containsKey(game.getName()))
                               .sorted(SORT_BY_PARENT_NAME)
                               .collect(toImmutableList());
    }

    public Game getGameByName(String name) {
        return dynamoDBMapper.load(Game.class, name);
    }

    public List<Game> getGamesByParent(String parent) {
        DynamoDBScanExpression expression = new DynamoDBScanExpression()
                .withFilterExpression("#parent = :parent")
                .withExpressionAttributeNames(ImmutableMap.of("#parent", "parent"))
                .withExpressionAttributeValues(ImmutableMap.of(":parent", new AttributeValue(parent)));
        return dynamoDBMapper.scan(Game.class, expression).stream().sorted(comparing(Game::getName)).collect(toImmutableList());
    }

    public void saveGame(Game game) {
        dynamoDBMapper.save(game);
    }
}

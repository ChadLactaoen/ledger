package com.lactaoen.ledger.service.data;

import com.lactaoen.ledger.model.Bet;
import com.lactaoen.ledger.model.Game;
import com.lactaoen.ledger.model.data.GamblingChartEntry;
import com.lactaoen.ledger.service.GameService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

import static com.google.common.collect.ImmutableList.toImmutableList;
import static java.util.stream.Collectors.groupingBy;

@Service
public class GamblingChartService {

    private static final String ROOT_PARENT = "None";

    private final GameService gameService;

    public GamblingChartService(GameService gameService) {
        this.gameService = gameService;
    }

    public List<GamblingChartEntry> createChart(List<Bet> bets, String parent) {
        List<Game> games = gameService.getGamesByParent(parent);
        Map<Game, List<Bet>> betMap = bets.stream()
                                          .filter(bet -> bet.getProfit() != null)
                                          .collect(groupingBy(ROOT_PARENT.equals(parent) ? collectByParentGame() : Bet::getGame));
        return games.stream().map(game -> createEntry(game, betMap)).collect(toImmutableList());
    }

    public GamblingChartEntry createTotalEntry(List<GamblingChartEntry> allEntries) {
        Game totalGame = new Game();
        totalGame.setName("Total");

        return new GamblingChartEntry.Builder()
                .setGame(totalGame)
                .setWins(allEntries.stream().mapToInt(GamblingChartEntry::getWins).sum())
                .setLosses(allEntries.stream().mapToInt(GamblingChartEntry::getLosses).sum())
                .setTies(allEntries.stream().mapToInt(GamblingChartEntry::getTies).sum())
                .setProfit(allEntries.stream().mapToDouble(GamblingChartEntry::getProfit).sum())
                .setWagered(allEntries.stream().mapToDouble(GamblingChartEntry::getWagered).sum())
                .build();
    }

    private GamblingChartEntry createEntry(Game game, Map<Game, List<Bet>> betMap) {
        return betMap.containsKey(game) ? createEntryForGame(game, betMap.get(game)) : createBlankEntry(game);
    }

    private Function<Bet, Game> collectByParentGame() {
        return bet -> {
            if (ROOT_PARENT.equals(bet.getGame().getParent())) {
                return bet.getGame();
            } else {
                Game game = new Game();
                game.setName(bet.getGame().getParent());
                game.setParent(ROOT_PARENT);
                return game;
            }
        };
    }

    private GamblingChartEntry createEntryForGame(Game game, List<Bet> bets) {
        int wins = (int) bets.stream().filter(bet -> bet.getProfit() != null && bet.getProfit() > 0).count();
        int losses = (int) bets.stream().filter(bet -> bet.getProfit() != null && bet.getProfit() < 0).count();
        int ties = bets.size() - (wins + losses);

        return new GamblingChartEntry.Builder()
                .setGame(game)
                .setProfit(bets.stream().mapToDouble(Bet::getProfit).filter(Objects::nonNull).sum())
                .setWagered(bets.stream().mapToDouble(Bet::getWager).filter(Objects::nonNull).sum())
                .setWins(wins)
                .setLosses(losses)
                .setTies(ties)
                .build();
    }

    private GamblingChartEntry createBlankEntry(Game game) {
        return new GamblingChartEntry.Builder()
                .setGame(game)
                .setProfit(0D)
                .setWagered(0D)
                .setWins(0)
                .setLosses(0)
                .setTies(0)
                .build();
    }
}

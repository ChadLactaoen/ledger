package com.lactaoen.ledger.controller.api;

import com.lactaoen.ledger.mapper.BetMapper;
import com.lactaoen.ledger.model.Bet;
import com.lactaoen.ledger.model.GraphCoordinate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping(value = "/api/graph")
public class GraphApi {

    private BetMapper betMapper;

    @SuppressWarnings("all")
    @RequestMapping(value = "/gambling")
    public List<GraphCoordinate<Date, Double>> getGamblingGraphForYear(@RequestParam(name = "year", required = false) Integer year) {
        if (year == null) {
            year = Calendar.getInstance().get(Calendar.YEAR);
        }

        List<Bet> bets = betMapper.getAllBetsByYear(year).stream().filter(bet -> bet.getProfit() != null).sorted(comparing(Bet::getDate)).collect(toList());
        Double runningTotal = 0D;
        List<GraphCoordinate<Date, Double>> coordinateList = new ArrayList<>();

        for (Bet bet : bets) {
            runningTotal += bet.getProfit();
            GraphCoordinate<Date, Double> lastCoordinate = coordinateList.isEmpty() ? null : coordinateList.get(coordinateList.size() - 1);

            if (!coordinateList.isEmpty() && lastCoordinate.getX().equals(bet.getDate())) {
                lastCoordinate.setY(runningTotal);
            } else {
                coordinateList.add(new GraphCoordinate<>(bet.getDate(), runningTotal));
            }
        }

        return coordinateList;
    }

    @Autowired
    public void setBetMapper(BetMapper betMapper) {
        this.betMapper = betMapper;
    }
}

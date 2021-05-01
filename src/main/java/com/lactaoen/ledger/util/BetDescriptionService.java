package com.lactaoen.ledger.util;

import com.lactaoen.ledger.model.Bet;
import com.lactaoen.ledger.model.Team;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class BetDescriptionService {

    public String createMainDescriptor(Bet bet) {
        String descriptor = "";

        if (bet == null) {
            return descriptor;
        }

        String parentGame = bet.getGame().getParent();

        if (parentGame.equals("Sports Betting")) {
            return createSportsBetDescriptor(bet, descriptor);
        }

        return StringUtils.isEmpty(bet.getMemo()) ? descriptor : bet.getMemo();
    }

    public String createMatchDescriptor(Bet bet) {
        Team forTeam = bet.getForTeam();
        Team againstTeam = bet.getAgainstTeam();

        return createTeamDescriptor(forTeam) + " vs " + createTeamDescriptor(againstTeam);
    }

    private String createSportsBetDescriptor(Bet bet, String descriptor) {
        // Odds
        if (bet.getOdds() == 100) {
            descriptor += "EVEN";
        } else if (bet.getOdds() > 0) {
            descriptor += "+" + bet.getOdds();
        } else {
            descriptor += bet.getOdds().toString();
        }

        // Matchup
        descriptor += " " + bet.getForTeam().getMascot() + " ";

        // Spread/Line
        String betType = bet.getBetType();
        if (("Moneyline").equals(betType)) {
            descriptor += " SU";
        } else if (("Over").equals(betType) || ("Under").equals(betType)) {
            descriptor += " " + bet.getLine() + betType.toLowerCase().charAt(0);
        } else {
            descriptor += " " + bet.getLine();
        }

        descriptor += " ";

        if (!"Full".equals(bet.getGameType())) {
            descriptor += " (" + bet.getGameType() + ")";
        }

        // Is Live
        if (bet.isLive()) {
            descriptor += " (Live)";
        }

        return descriptor;
    }

    private String createTeamDescriptor(Team team) {
        return team.getLocation() + " " + team.getMascot();
    }
}

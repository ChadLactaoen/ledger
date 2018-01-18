package com.lactaoen.ledger.controller.api;

import com.lactaoen.ledger.mapper.BetMapper;
import com.lactaoen.ledger.mapper.GameMapper;
import com.lactaoen.ledger.mapper.SportsBetMapper;
import com.lactaoen.ledger.model.Bet;
import com.lactaoen.ledger.model.form.BetForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@RestController
@RequestMapping("/api/bet")
public class BetController extends AbstractApiController {

    private BetMapper betMapper;

    private GameMapper gameMapper;

    private SportsBetMapper sportsBetMapper;

    @RequestMapping(method = RequestMethod.GET)
    public List<Bet> getAllBets() {
        return betMapper.getAllBets();
    }

    @RequestMapping(value = "/unresolved", method = RequestMethod.GET)
    public List<Bet> getUnresolvedBets() {
        return betMapper.getUnresolvedBets();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Bet getBet(@PathVariable("id") int id) {
        return betMapper.getById(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public RedirectView createBet(@ModelAttribute("bet") BetForm bet, RedirectAttributes model) {
        int rowsInserted = betMapper.createBet(bet);

        if (rowsInserted > 0 && isSportsBet(bet.getGameId())) {
            sportsBetMapper.createSportsBet(bet);
        }

        generateFlashAttributes(model, rowsInserted, "bet", PostType.ADD);
        return new RedirectView("/form/bet");
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public RedirectView updateBet(@PathVariable("id") int id, @ModelAttribute BetForm bet, RedirectAttributes model) {
        bet.setBetId(id);

        int rowsUpdated = betMapper.updateBet(bet);

        if (isSportsBet(bet.getGameId())) {
            if (sportsBetMapper.getSportsBetByBetId(id) == null) {
                rowsUpdated += sportsBetMapper.createSportsBet(bet);
            } else {
                rowsUpdated += sportsBetMapper.updateSportsBet(bet);
            }
        }

        generateFlashAttributes(model, rowsUpdated , "bet", PostType.UPDATE);
        return new RedirectView("/");
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteBet(@PathVariable("id") int id) {
        betMapper.deleteBet(id);
    }

    private boolean isSportsBet(int gameId) {
        return gameMapper.isSportsBet(gameId);
    }

    @Autowired
    public void setBetMapper(BetMapper betMapper) {
        this.betMapper = betMapper;
    }

    @Autowired
    public void setGameMapper(GameMapper gameMapper) {
        this.gameMapper = gameMapper;
    }

    @Autowired
    public void setSportsBetMapper(SportsBetMapper sportsBetMapper) {
        this.sportsBetMapper = sportsBetMapper;
    }
}

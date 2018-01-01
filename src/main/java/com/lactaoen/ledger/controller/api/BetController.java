package com.lactaoen.ledger.controller.api;

import com.lactaoen.ledger.mapper.BetMapper;
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

    @RequestMapping(method = RequestMethod.GET)
    public List<Bet> getAllBets() {
        return betMapper.getAllBets();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Bet getBet(@PathVariable("id") int id) {
        return betMapper.getById(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public RedirectView createBet(@ModelAttribute("bet") BetForm bet, RedirectAttributes model) {
        generateFlashAttributes(model, betMapper.createBet(bet), "bet", PostType.ADD);
        return new RedirectView("/form/bet");
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public RedirectView updateBet(@PathVariable("id") int id, @ModelAttribute BetForm bet, RedirectAttributes model) {
        bet.setBetId(id);
        generateFlashAttributes(model, betMapper.updateBet(bet), "bet", PostType.UPDATE);
        return new RedirectView("/");
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteBet(@PathVariable("id") int id) {
        betMapper.deleteBet(id);
    }

    @Autowired
    public void setBetMapper(BetMapper betMapper) {
        this.betMapper = betMapper;
    }
}

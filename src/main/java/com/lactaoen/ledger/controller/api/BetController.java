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
public class BetController {

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
        if (betMapper.createBet(bet) != 0) {
            model.addFlashAttribute("msgClass", "success");
            model.addFlashAttribute("msg", "The bet was added successfully");
        } else {
            model.addFlashAttribute("msgClass", "danger");
            model.addFlashAttribute("msg", "There was an issue adding the bet");
        }

        return new RedirectView("/");
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public int updateBet(@PathVariable("id") int id, @RequestBody Bet bet) {
        return 1;
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

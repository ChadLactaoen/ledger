package com.lactaoen.ledger.controller;

import com.lactaoen.ledger.model.Bet;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bet")
public class BetController {

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Bet getBet(@PathVariable("id") int id) {
        return null;
    }

    @RequestMapping(method = RequestMethod.POST)
    public int createBet(@RequestBody Bet bet) {
        return 1;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public int updateBet(@PathVariable("id") int id, @RequestBody Bet bet) {
        return 1;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteBet(@PathVariable("id") int id) {

    }
}

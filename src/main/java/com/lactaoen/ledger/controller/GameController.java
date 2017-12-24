package com.lactaoen.ledger.controller;

import com.lactaoen.ledger.model.Game;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/game")
public class GameController {

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Game getGame(@PathVariable("id") int id) {
        return null;
    }

    @RequestMapping(method = RequestMethod.POST)
    public int createGame(@RequestBody Game game) {
        return 1;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public int updateGame(@PathVariable("id") int id, @RequestBody Game game) {
        return 1;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteGame(@PathVariable("id") int id) {

    }
}

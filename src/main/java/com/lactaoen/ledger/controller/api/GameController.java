package com.lactaoen.ledger.controller.api;

import com.lactaoen.ledger.mapper.GameMapper;
import com.lactaoen.ledger.model.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/game")
public class GameController {

    private GameMapper gameMapper;

    @RequestMapping(method = RequestMethod.GET)
    public List<Game> getAllGames() {
        return gameMapper.getAllGames();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Game getGame(@PathVariable("id") int id) {
        return gameMapper.getGameById(id);
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

    @Autowired
    public void setGameMapper(GameMapper gameMapper) {
        this.gameMapper = gameMapper;
    }
}

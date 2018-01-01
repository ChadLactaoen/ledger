package com.lactaoen.ledger.controller.api;

import com.lactaoen.ledger.mapper.GameMapper;
import com.lactaoen.ledger.model.Game;
import com.lactaoen.ledger.model.form.GameForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@RestController
@RequestMapping("/api/game")
public class GameController extends AbstractApiController {

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
    public RedirectView createGame(@ModelAttribute("game") GameForm game, RedirectAttributes model) {
        generateFlashAttributes(model, gameMapper.createGame(game), "game", PostType.ADD);
        return new RedirectView("/form/game");
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public RedirectView updateGame(@PathVariable("id") int id, @ModelAttribute("game") GameForm game, RedirectAttributes model) {
        game.setGameId(id);
        generateFlashAttributes(model, gameMapper.updateGame(game), "game", PostType.UPDATE);
        return new RedirectView("/");
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteGame(@PathVariable("id") int id) {
        gameMapper.deleteGame(id);
    }

    @Autowired
    public void setGameMapper(GameMapper gameMapper) {
        this.gameMapper = gameMapper;
    }
}

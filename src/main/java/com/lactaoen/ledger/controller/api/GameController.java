package com.lactaoen.ledger.controller.api;

import com.lactaoen.ledger.mapper.GameMapper;
import com.lactaoen.ledger.model.Game;
import com.lactaoen.ledger.model.form.GameForm;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@RestController
@RequestMapping("/api/game")
public class GameController extends AbstractApiController {

    private final GameMapper gameMapper;

    public GameController(GameMapper gameMapper) {
        this.gameMapper = gameMapper;
    }

    @GetMapping
    public List<Game> getAllGames() {
        return gameMapper.getAllGames();
    }

    @GetMapping("/permissible")
    public List<Game> getPermissibleGames() {
        return gameMapper.getPermissibleGames();
    }

    @GetMapping("/{id}")
    public Game getGame(@PathVariable("id") int id) {
        return gameMapper.getGameById(id);
    }

    @PostMapping
    public RedirectView createGame(@ModelAttribute("game") GameForm game, RedirectAttributes model) {
        generateFlashAttributes(model, gameMapper.createGame(game), "game", PostType.ADD);
        return new RedirectView("/form/game");
    }

    @PostMapping(value = "/{id}")
    public RedirectView updateGame(@PathVariable("id") int id, @ModelAttribute("game") GameForm game, RedirectAttributes model) {
        game.setGameId(id);
        generateFlashAttributes(model, gameMapper.updateGame(game), "game", PostType.UPDATE);
        return new RedirectView("/");
    }

    @DeleteMapping("/{id}")
    public void deleteGame(@PathVariable("id") int id) {
        gameMapper.deleteGame(id);
    }
}

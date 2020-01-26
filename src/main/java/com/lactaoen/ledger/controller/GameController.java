package com.lactaoen.ledger.controller;

import com.lactaoen.ledger.model.Game;
import com.lactaoen.ledger.service.FlashAttributeService;
import com.lactaoen.ledger.service.GameService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("game")
public class GameController {

    private final GameService gameService;
    private final FlashAttributeService flashAttributeService;

    public GameController(GameService gameService, FlashAttributeService flashAttributeService) {
        this.gameService = gameService;
        this.flashAttributeService = flashAttributeService;
    }

    @GetMapping
    public ModelAndView getGameForm(@RequestParam(value = "name", required = false) String name) {
        Game game = null;
        if (name != null) {
            game = gameService.getGameByName(name);
        }

        ModelAndView mav = new ModelAndView("game");
        mav.addObject("game", game == null ? new Game() : game);
        mav.addObject("games", gameService.getAllGames(GameService.SORT_BY_GAME_NAME));

        return mav;
    }

    @PostMapping
    public RedirectView postGameForm(@ModelAttribute("game") Game game, RedirectAttributes redirectAttributes) {
        boolean exists = gameService.getGameByName(game.getName()) != null;

        FlashAttributeService.PostType postType = exists ? FlashAttributeService.PostType.UPDATE : FlashAttributeService.PostType.ADD;
        gameService.saveGame(game);

        boolean success = gameService.getGameByName(game.getName()) != null;
        flashAttributeService.generateFlashAttributes(redirectAttributes, success, "game", postType);

        return new RedirectView("/game");
    }
}

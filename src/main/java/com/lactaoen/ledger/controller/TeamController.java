package com.lactaoen.ledger.controller;

import com.lactaoen.ledger.model.Team;
import com.lactaoen.ledger.service.FlashAttributeService;
import com.lactaoen.ledger.service.GameService;
import com.lactaoen.ledger.service.TeamService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@RestController
@RequestMapping("team")
public class TeamController extends BaseController {

    private final GameService gameService;
    private final TeamService teamService;
    private final FlashAttributeService flashAttributeService;

    public TeamController(GameService gameService, TeamService teamService, FlashAttributeService flashAttributeService) {
        this.gameService = gameService;
        this.teamService = teamService;
        this.flashAttributeService = flashAttributeService;
    }

    @GetMapping("list")
    public List<Team> getTeamsBySport(@RequestParam("game") String game) {
        return teamService.getTeamsByGame(game);
    }

    @GetMapping
    public ModelAndView getTeamForm(@RequestParam(value = "location", required = false) String location,
                                    @RequestParam(value = "mascot", required = false) String mascot) {
        Team team = null;
        if (location != null && mascot != null) {
            team = teamService.getTeamByLocationAndMascot(location, mascot);
        }

        ModelAndView mav = new ModelAndView("team");
        mav.addObject("team", team == null ? new Team() : team);
        mav.addObject("games", gameService.getGamesByParent("Sports Betting"));

        return mav;
    }

    @PostMapping
    public RedirectView postTeamForm(@ModelAttribute("team") Team team, RedirectAttributes redirectAttributes) {
        boolean exists = teamService.getTeamByLocationAndMascot(team.getLocation(), team.getMascot()) != null;

        FlashAttributeService.PostType postType  = exists ? FlashAttributeService.PostType.UPDATE : FlashAttributeService.PostType.ADD;
        teamService.saveTeam(team);
        boolean success = teamService.getTeamByLocationAndMascot(team.getLocation(), team.getMascot()).equals(team);
        flashAttributeService.generateFlashAttributes(redirectAttributes, success, "team", postType);

        return new RedirectView("team");
    }
}

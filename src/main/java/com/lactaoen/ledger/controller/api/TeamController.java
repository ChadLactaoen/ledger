package com.lactaoen.ledger.controller.api;

import com.lactaoen.ledger.mapper.TeamMapper;
import com.lactaoen.ledger.model.Team;
import com.lactaoen.ledger.model.form.TeamForm;
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
@RequestMapping("/api/team")
public class TeamController extends AbstractApiController {

    private final TeamMapper teamMapper;

    public TeamController(TeamMapper teamMapper) {
        this.teamMapper = teamMapper;
    }

    @GetMapping("/game/{id}")
    public List<Team> getTeamsByGameId(@PathVariable("id") int id) {
        return teamMapper.getTeamsByGameId(id);
    }

    @GetMapping("/{id}")
    public Team getTeamById(@PathVariable("id") int id) {
        return teamMapper.getTeamById(id);
    }

    @PostMapping
    public RedirectView createTeam(@ModelAttribute("team") TeamForm team, RedirectAttributes model) {
        generateFlashAttributes(model, teamMapper.createTeam(team), "team", PostType.ADD);
        return new RedirectView("/form/team");
    }

    @PostMapping("/{id}")
    public RedirectView updateTeam(@PathVariable("id") int id, @ModelAttribute("team") TeamForm team, RedirectAttributes model) {
        team.setTeamId(id);
        generateFlashAttributes(model, teamMapper.updateTeam(team), "team", PostType.UPDATE);
        return new RedirectView("/");
    }

    @DeleteMapping("/{id}")
    public void deleteTeam(@PathVariable("id") int id) {
        teamMapper.deleteTeam(id);
    }
}

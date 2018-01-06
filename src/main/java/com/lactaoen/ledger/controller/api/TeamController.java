package com.lactaoen.ledger.controller.api;

import com.lactaoen.ledger.mapper.TeamMapper;
import com.lactaoen.ledger.model.Team;
import com.lactaoen.ledger.model.form.TeamForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@RestController
@RequestMapping("/api/team")
public class TeamController extends AbstractApiController {

    private TeamMapper teamMapper;

    @RequestMapping(value = "/game/{id}", method = RequestMethod.GET)
    public List<Team> getTeamsByGameId(@PathVariable("id") int id) {
        return teamMapper.getTeamsByGameId(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Team getTeamById(@PathVariable("id") int id) {
        return teamMapper.getTeamById(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public RedirectView createTeam(@ModelAttribute("team") TeamForm team, RedirectAttributes model) {
        generateFlashAttributes(model, teamMapper.createTeam(team), "team", PostType.ADD);
        return new RedirectView("/form/team");
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public RedirectView updateTeam(@PathVariable("id") int id, @ModelAttribute("team") TeamForm team, RedirectAttributes model) {
        team.setTeamId(id);
        generateFlashAttributes(model, teamMapper.updateTeam(team), "team", PostType.UPDATE);
        return new RedirectView("/");
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteTeam(@PathVariable("id") int id) {
        teamMapper.deleteTeam(id);
    }

    @Autowired
    public void setTeamMapper(TeamMapper teamMapper) {
        this.teamMapper = teamMapper;
    }
}

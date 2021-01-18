package com.lactaoen.ledger.controller;

import com.amazonaws.util.StringUtils;
import com.lactaoen.ledger.model.Bet;
import com.lactaoen.ledger.model.Template;
import com.lactaoen.ledger.model.form.BetForm;
import com.lactaoen.ledger.model.template.Type;
import com.lactaoen.ledger.service.BetService;
import com.lactaoen.ledger.service.FlashAttributeService;
import com.lactaoen.ledger.service.GameService;
import com.lactaoen.ledger.service.TeamService;
import com.lactaoen.ledger.service.TemplateService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Map;
import java.util.function.Function;

import static com.google.common.collect.ImmutableMap.toImmutableMap;
import static java.util.Comparator.comparing;

@RestController
@RequestMapping("bet")
public class BetController {

    private final BetService betService;
    private final GameService gameService;
    private final TeamService teamService;
    private final TemplateService templateService;
    private final FlashAttributeService flashAttributeService;

    public BetController(BetService betService,
                         GameService gameService,
                         TeamService teamService,
                         TemplateService templateService,
                         FlashAttributeService flashAttributeService) {
        this.betService = betService;
        this.gameService = gameService;
        this.teamService = teamService;
        this.templateService = templateService;
        this.flashAttributeService = flashAttributeService;
    }

    @GetMapping
    public ModelAndView getBetForm(@RequestParam(value = "betId", required = false) String betId) {
        Bet bet = null;
        if (betId != null) {
            bet = betService.getBetById(betId);
        }

        boolean isSport = bet != null && bet.getGame().getParent().equals("Sports Betting");

        Map<String, Template> transactionTemplates = templateService.getTemplatesByType(Type.BET)
                .stream()
                .sorted(comparing(Template::getTemplateName))
                .collect(toImmutableMap(Template::getTemplateName, Function.identity()));

        ModelAndView mav = new ModelAndView("bet");
        mav.addObject("betForm", bet == null ? new BetForm() : bet.toForm());
        mav.addObject("games", gameService.getLowestLevelGames());
        mav.addObject("isSport", isSport);
        mav.addObject("templateNames", transactionTemplates.keySet());
        mav.addObject("templates", transactionTemplates);
        if (isSport) {
            mav.addObject("teams", teamService.getTeamsByGame(bet.getGame().getName()));
        }
        return mav;
    }

    @GetMapping("/win/{betId}")
    public RedirectView setBetAsWin(@PathVariable("betId") String betId, RedirectAttributes model) {
        return updateBetAndRedirect(betId,model, true);
    }

    @GetMapping("/loss/{betId}")
    public RedirectView setBetAsLoss(@PathVariable("betId") String betId, RedirectAttributes model) {
        return updateBetAndRedirect(betId,model, false);
    }

    @PostMapping
    public RedirectView postBetForm(@ModelAttribute("betForm") BetForm betForm, RedirectAttributes redirectAttributes) {
        boolean exists = !StringUtils.isNullOrEmpty(betForm.getBetId()) && betService.getBetById(betForm.getBetId()) != null;

        FlashAttributeService.PostType postType  = exists ? FlashAttributeService.PostType.UPDATE : FlashAttributeService.PostType.ADD;
        betService.saveBet(betForm);
        flashAttributeService.generateFlashAttributes(redirectAttributes, true, "bet", postType);

        return new RedirectView("bet");
    }

    private RedirectView updateBetAndRedirect(String betId, RedirectAttributes model, boolean win) {
        betService.updateBet(betId, win);
        flashAttributeService.generateFlashAttributes(model, betService.getBetById(betId).getProfit() != null, "bet", FlashAttributeService.PostType.UPDATE);
        return new RedirectView("/gambling");
    }
}

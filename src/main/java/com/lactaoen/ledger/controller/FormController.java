package com.lactaoen.ledger.controller;

import com.lactaoen.ledger.mapper.*;
import com.lactaoen.ledger.model.*;
import com.lactaoen.ledger.model.form.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Controller
@RequestMapping("/form")
public class FormController {

    @Autowired
    private AllocationMapper allocationMapper;

    @Autowired
    private BetMapper betMapper;

    @Autowired
    private CasinoMapper casinoMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private GameMapper gameMapper;

    @Autowired
    private PeriodMapper periodMapper;

    @Autowired
    private SportsBetMapper sportsBetMapper;

    @Autowired
    private TeamMapper teamMapper;

    @Autowired
    private TransactionMapper transactionMapper;

    @RequestMapping(value = "/bet", method = RequestMethod.GET)
    public String getBetView(Model model) {
        model.addAttribute("bet", new BetForm());
        model.addAttribute("isSport", false);
        model.addAttribute("games", gameMapper.getPermissibleGames());
        model.addAttribute("casinos", casinoMapper.getAllCasinos());
        model.addAttribute("gameTypes", sportsBetMapper.getAllGameTypes());
        model.addAttribute("betTypes", sportsBetMapper.getAllBetTypes());

        return "bet";
    }

    @RequestMapping(value = "/bet/{betId}", method = RequestMethod.GET)
    public String getUpdateBetView(@PathVariable("betId") int betId, Model model) {
        BetForm betForm = betMapper.getById(betId).toBetForm();

        if (gameMapper.isSportsBet(betForm.getGameId())) {
            betForm.addSportsBet(sportsBetMapper.getSportsBetByBetId(betId));
            model.addAttribute("teams", teamMapper.getTeamsByGameId(betForm.getGameId()));
            model.addAttribute("isSport", true);
        } else {
            model.addAttribute("isSport", false);
        }

        model.addAttribute("bet", betForm);
        model.addAttribute("games", gameMapper.getPermissibleGames());
        model.addAttribute("casinos", casinoMapper.getAllCasinos());
        model.addAttribute("gameTypes", sportsBetMapper.getAllGameTypes());
        model.addAttribute("betTypes", sportsBetMapper.getAllBetTypes());

        return "bet";
    }

    @RequestMapping(value = "/casino", method = RequestMethod.GET)
    public String getCasinoView(Model model) {
        model.addAttribute("casino", new Casino());
        return "casino";
    }

    @RequestMapping(value = "/casino/{casinoId}", method = RequestMethod.GET)
    public String getUpdateCasinoView(@PathVariable("casinoId") int casinoId, Model model) {
        model.addAttribute("casino", casinoMapper.getCasinoById(casinoId));

        return "casino";
    }

    @RequestMapping(value = "/category", method = RequestMethod.GET)
    public String getCategoryView(Model model) {
        model.addAttribute("category", new CategoryForm());
        model.addAttribute("categories", categoryMapper.getAllParentCategories());

        return "category";
    }

    @RequestMapping(value = "/category/{categoryId}", method = RequestMethod.GET)
    public String getUpdateCategoryView(@PathVariable("categoryId") int categoryId, Model model) {
        model.addAttribute("category", categoryMapper.getCategoryById(categoryId).toCategoryForm());
        model.addAttribute("categories", categoryMapper.getAllParentCategories());

        return "category";
    }

    @RequestMapping(value = "/game", method = RequestMethod.GET)
    public String getGameView(Model model) {
        model.addAttribute("game", new GameForm());
        model.addAttribute("games", gameMapper.getAllGames());

        return "game";
    }

    @RequestMapping(value = "/game/{gameId}", method = RequestMethod.GET)
    public String getUpdateGameView(@PathVariable("gameId") int gameId, Model model) {
        model.addAttribute("game", gameMapper.getGameById(gameId).toGameForm());
        model.addAttribute("games", gameMapper.getAllGames());

        return "game";
    }

    @RequestMapping(value = "/period", method = RequestMethod.GET)
    public String getPeriodView(Model model) {
        model.addAttribute("period", new PeriodForm());
        model.addAttribute("categories", categoryMapper.getAllChildCategories().stream().sorted(byParentCategory.thenComparing(byCategoryName)).collect(toList()));

        return "period";
    }

    @RequestMapping(value = "/period/{periodId}", method = RequestMethod.GET)
    public String getUpdatePeriodView(@PathVariable("periodId") int periodId, Model model) {
        Period period = periodMapper.getPeriodById(periodId);
        List<Allocation> allocations = allocationMapper.getAllocationsByPeriodId(periodId);
        Map<Integer, Double> categoryTotalMap = allocations.stream().collect(Collectors.toMap(allocation -> allocation.getCategory().getCategoryId(), Allocation::getTotal));
        List<Category> categories = categoryMapper.getAllChildCategories().stream().sorted(byParentCategory.thenComparing(byCategoryName)).collect(toList());

        List<String> categoryIds = new ArrayList<>();
        List<String> amounts = new ArrayList<>();
        for (Category category : categories) {
            categoryIds.add(category.getCategoryId().toString());
            if (categoryTotalMap.containsKey(category.getCategoryId())) {
                amounts.add(categoryTotalMap.get(category.getCategoryId()).toString());
            } else {
                amounts.add("-1");
            }
        }

        PeriodForm form = new PeriodForm();
        form.setPeriodId(periodId);
        form.setTotal(period.getTotal());
        form.setStartDate(period.getStartDate());
        form.setEndDate(period.getEndDate());
        form.setCategoryIds(categoryIds);
        form.setAmounts(amounts);

        model.addAttribute("period", form);
        model.addAttribute("categories", categories);

        return "period";
    }

    @RequestMapping(value = "/team", method = RequestMethod.GET)
    public String getTeamView(Model model) {
        model.addAttribute("team", new TeamForm());
        model.addAttribute("games", getSportsBetGames());
        return "team";
    }

    @RequestMapping(value = "/team/{teamId}", method = RequestMethod.GET)
    public String getUpdateTeamView(@PathVariable("teamId") int teamId, Model model) {
        model.addAttribute("team", teamMapper.getTeamById(teamId).toTeamForm());
        model.addAttribute("games", getSportsBetGames());
        return "team";
    }

    @RequestMapping(value = "/transaction", method = RequestMethod.GET)
    public String getTransactionView(Model model) {
        model.addAttribute("transaction", new TransactionForm());
        model.addAttribute("categories", categoryMapper.getAllChildCategories());

        return "transaction";
    }

    @RequestMapping(value = "/transaction/{transactionId}", method = RequestMethod.GET)
    public String getUpdateTransactionView(@PathVariable("transactionId") int transactionId, Model model) {
        model.addAttribute("transaction", transactionMapper.getTransactionById(transactionId).toTransactionForm());
        model.addAttribute("categories", categoryMapper.getAllChildCategories());

        return "transaction";
    }

    private List<Game> getSportsBetGames() {
        return gameMapper.getAllGames().stream().filter(filterSportsCategory).sorted(byGameName).collect(toList());
    }

    private Predicate<Game> filterSportsCategory = game -> game.getParentGame() != null && game.getParentGame().getName().equals("Sports Betting");

    private Comparator<Category> byParentCategory = Comparator.comparing(category -> category.getParentCategory().getCategoryId());

    private Comparator<Category> byCategoryName = Comparator.comparing(Category::getName);

    private Comparator<Game> byGameName = Comparator.comparing(Game::getName);
}

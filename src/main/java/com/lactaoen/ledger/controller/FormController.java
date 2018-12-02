package com.lactaoen.ledger.controller;

import com.lactaoen.ledger.mapper.AllocationMapper;
import com.lactaoen.ledger.mapper.BetMapper;
import com.lactaoen.ledger.mapper.CasinoMapper;
import com.lactaoen.ledger.mapper.CategoryMapper;
import com.lactaoen.ledger.mapper.GameMapper;
import com.lactaoen.ledger.mapper.PeriodMapper;
import com.lactaoen.ledger.mapper.SportsBetMapper;
import com.lactaoen.ledger.mapper.TeamMapper;
import com.lactaoen.ledger.mapper.TransactionMapper;
import com.lactaoen.ledger.model.Allocation;
import com.lactaoen.ledger.model.Casino;
import com.lactaoen.ledger.model.Category;
import com.lactaoen.ledger.model.Game;
import com.lactaoen.ledger.model.Period;
import com.lactaoen.ledger.model.form.BetForm;
import com.lactaoen.ledger.model.form.CategoryForm;
import com.lactaoen.ledger.model.form.GameForm;
import com.lactaoen.ledger.model.form.PeriodForm;
import com.lactaoen.ledger.model.form.TeamForm;
import com.lactaoen.ledger.model.form.TransactionForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Controller
@RequestMapping("/form")
public class FormController {

    private static final Predicate<Game> FILTER_SPORTS_CATEGORY = game -> game.getParentGame() != null && game.getParentGame().getName().equals("Sports Betting");
    private static final Comparator<Category> BY_PARENT_CATEGORY = Comparator.comparing(category -> category.getParentCategory().getCategoryId());
    private static final Comparator<Category> BY_CATEGORY_NAME = Comparator.comparing(Category::getName);
    private static final Comparator<Game> BY_GAME_NAME = Comparator.comparing(Game::getName);

    private final AllocationMapper allocationMapper;
    private final BetMapper betMapper;
    private final CasinoMapper casinoMapper;
    private final CategoryMapper categoryMapper;
    private final GameMapper gameMapper;
    private final PeriodMapper periodMapper;
    private final SportsBetMapper sportsBetMapper;
    private final TeamMapper teamMapper;
    private final TransactionMapper transactionMapper;

    public FormController(AllocationMapper allocationMapper, BetMapper betMapper, CasinoMapper casinoMapper,
                          CategoryMapper categoryMapper, GameMapper gameMapper, PeriodMapper periodMapper,
                          SportsBetMapper sportsBetMapper, TeamMapper teamMapper, TransactionMapper transactionMapper) {
        this.allocationMapper = allocationMapper;
        this.betMapper = betMapper;
        this.casinoMapper = casinoMapper;
        this.categoryMapper = categoryMapper;
        this.gameMapper = gameMapper;
        this.periodMapper = periodMapper;
        this.sportsBetMapper = sportsBetMapper;
        this.teamMapper = teamMapper;
        this.transactionMapper = transactionMapper;
    }

    @GetMapping(value = "/bet")
    public String getBetView(Model model) {
        model.addAttribute("bet", new BetForm());
        model.addAttribute("isSport", false);
        model.addAttribute("games", gameMapper.getPermissibleGames());
        model.addAttribute("casinos", casinoMapper.getAllCasinos());
        model.addAttribute("gameTypes", sportsBetMapper.getAllGameTypes());
        model.addAttribute("betTypes", sportsBetMapper.getAllBetTypes());

        return "bet";
    }

    @GetMapping("/bet/{betId}")
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

    @GetMapping("/casino")
    public String getCasinoView(Model model) {
        model.addAttribute("casino", new Casino());
        return "casino";
    }

    @GetMapping("/casino/{casinoId}")
    public String getUpdateCasinoView(@PathVariable("casinoId") int casinoId, Model model) {
        model.addAttribute("casino", casinoMapper.getCasinoById(casinoId));

        return "casino";
    }

    @GetMapping("/category")
    public String getCategoryView(Model model) {
        model.addAttribute("category", new CategoryForm());
        model.addAttribute("categories", categoryMapper.getAllParentCategories());

        return "category";
    }

    @GetMapping("/category/{categoryId}")
    public String getUpdateCategoryView(@PathVariable("categoryId") int categoryId, Model model) {
        model.addAttribute("category", categoryMapper.getCategoryById(categoryId).toCategoryForm());
        model.addAttribute("categories", categoryMapper.getAllParentCategories());

        return "category";
    }

    @GetMapping("/game")
    public String getGameView(Model model) {
        model.addAttribute("game", new GameForm());
        model.addAttribute("games", gameMapper.getAllGames());

        return "game";
    }

    @GetMapping("/game/{gameId}")
    public String getUpdateGameView(@PathVariable("gameId") int gameId, Model model) {
        model.addAttribute("game", gameMapper.getGameById(gameId).toGameForm());
        model.addAttribute("games", gameMapper.getAllGames());

        return "game";
    }

    @GetMapping("/period")
    public String getPeriodView(Model model) {
        model.addAttribute("period", new PeriodForm());
        model.addAttribute("categories", categoryMapper.getAllChildCategories().stream().sorted(BY_PARENT_CATEGORY.thenComparing(BY_CATEGORY_NAME)).collect(toList()));

        return "period";
    }

    @GetMapping("/period/{periodId}")
    public String getUpdatePeriodView(@PathVariable("periodId") int periodId, Model model) {
        Period period = periodMapper.getPeriodById(periodId);
        List<Allocation> allocations = allocationMapper.getAllocationsByPeriodId(periodId);
        Map<Integer, Double> categoryTotalMap = allocations.stream().collect(Collectors.toMap(allocation -> allocation.getCategory().getCategoryId(), Allocation::getTotal));
        List<Category> categories = categoryMapper.getAllChildCategories().stream().sorted(BY_PARENT_CATEGORY.thenComparing(BY_CATEGORY_NAME)).collect(toList());

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

    @GetMapping("/team")
    public String getTeamView(Model model) {
        model.addAttribute("team", new TeamForm());
        model.addAttribute("games", getSportsBetGames());
        return "team";
    }

    @GetMapping("/team/{teamId}")
    public String getUpdateTeamView(@PathVariable("teamId") int teamId, Model model) {
        model.addAttribute("team", teamMapper.getTeamById(teamId).toTeamForm());
        model.addAttribute("games", getSportsBetGames());
        return "team";
    }

    @GetMapping("/transaction")
    public String getTransactionView(Model model) {
        model.addAttribute("transaction", new TransactionForm());
        model.addAttribute("categories", categoryMapper.getAllChildCategories());

        return "transaction";
    }

    @GetMapping("/transaction/{transactionId}")
    public String getUpdateTransactionView(@PathVariable("transactionId") int transactionId, Model model) {
        model.addAttribute("transaction", transactionMapper.getTransactionById(transactionId).toTransactionForm());
        model.addAttribute("categories", categoryMapper.getAllChildCategories());

        return "transaction";
    }

    private List<Game> getSportsBetGames() {
        return gameMapper.getAllGames().stream().filter(FILTER_SPORTS_CATEGORY).sorted(BY_GAME_NAME).collect(toList());
    }
}

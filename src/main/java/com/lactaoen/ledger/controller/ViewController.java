package com.lactaoen.ledger.controller;

import com.lactaoen.ledger.mapper.*;
import com.lactaoen.ledger.model.Casino;
import com.lactaoen.ledger.model.Period;
import com.lactaoen.ledger.model.form.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class ViewController {

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
    private TransactionMapper transactionMapper;

    @RequestMapping(method = RequestMethod.GET)
    public String getHome(Model model) {
        Period period = periodMapper.getCurrentPeriod();

        if (period == null) {
            period = periodMapper.getPeriodById(periodMapper.getLastPeriodId());
            model.addAttribute("msgClass", "danger");
            model.addAttribute("msg", "You are viewing an out-of-date period. Please create a new period as soon as possible.");
        }

        model.addAttribute("period", period);
        return "home";
    }

    @RequestMapping(value = "/bet", method = RequestMethod.GET)
    public String getBetView(Model model) {
        model.addAttribute("bet", new BetForm());
        model.addAttribute("games", gameMapper.getAllGames());
        model.addAttribute("casinos", casinoMapper.getAllCasinos());

        return "bet";
    }

    @RequestMapping(value = "/bet/{betId}", method = RequestMethod.GET)
    public String getUpdateBetView(@PathVariable("betId") int betId, Model model) {
        model.addAttribute("bet", betMapper.getById(betId).toBetForm());
        model.addAttribute("games", gameMapper.getAllGames());
        model.addAttribute("casinos", casinoMapper.getAllCasinos());

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
        model.addAttribute("categories", categoryMapper.getAllChildCategories());

        return "period";
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
}

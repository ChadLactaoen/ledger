package com.lactaoen.ledger.controller;

import com.lactaoen.ledger.mapper.CasinoMapper;
import com.lactaoen.ledger.mapper.CategoryMapper;
import com.lactaoen.ledger.mapper.GameMapper;
import com.lactaoen.ledger.mapper.PeriodMapper;
import com.lactaoen.ledger.model.Casino;
import com.lactaoen.ledger.model.form.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class ViewController {

    @Autowired
    private GameMapper gameMapper;

    @Autowired
    private CasinoMapper casinoMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private PeriodMapper periodMapper;

    @RequestMapping(method = RequestMethod.GET)
    public String getHome(Model model) {
        model.addAttribute("period", periodMapper.getPeriodById(1));
        return "home";
    }

    @RequestMapping(value = "/bet", method = RequestMethod.GET)
    public String getBetView(Model model) {
        model.addAttribute("bet", new BetForm());
        model.addAttribute("games", gameMapper.getAllGames());
        model.addAttribute("casinos", casinoMapper.getAllCasinos());

        return "bet";
    }

    @RequestMapping(value = "/casino", method = RequestMethod.GET)
    public String getCasinoView(Model model) {
        model.addAttribute("casino", new Casino());
        return "casino";
    }

    @RequestMapping(value = "/category", method = RequestMethod.GET)
    public String getCategoryView(Model model) {
        model.addAttribute("category", new CategoryForm());
        model.addAttribute("categories", categoryMapper.getAllParentCategories());

        return "category";
    }

    @RequestMapping(value = "/game", method = RequestMethod.GET)
    public String getGameView(Model model) {
        model.addAttribute("game", new GameForm());
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
}

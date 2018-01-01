package com.lactaoen.ledger.controller.api;

import com.lactaoen.ledger.mapper.CasinoMapper;
import com.lactaoen.ledger.model.Casino;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@RestController
@RequestMapping("/api/casino")
public class CasinoController extends AbstractApiController {

    private CasinoMapper casinoMapper;

    @RequestMapping(method = RequestMethod.GET)
    public List<Casino> getAllCasinos() {
        return casinoMapper.getAllCasinos();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Casino getCasino(@PathVariable("id") int id) {
        return casinoMapper.getCasinoById(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public RedirectView createCasino(@ModelAttribute("casino") Casino casino, RedirectAttributes model) {
        generateFlashAttributes(model, casinoMapper.createCasino(casino), "casino", PostType.ADD);
        return new RedirectView("/form/casino");
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public RedirectView updateCasino(@PathVariable("id") int id, @ModelAttribute("casino") Casino casino, RedirectAttributes model) {
        casino.setCasinoId(id);
        generateFlashAttributes(model, casinoMapper.updateCasino(casino), "casino", PostType.UPDATE);
        return new RedirectView("/");
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteCasino(@PathVariable("id") int id) {
        casinoMapper.deleteCasino(id);
    }

    @Autowired
    public void setCasinoMapper(CasinoMapper casinoMapper) {
        this.casinoMapper = casinoMapper;
    }
}

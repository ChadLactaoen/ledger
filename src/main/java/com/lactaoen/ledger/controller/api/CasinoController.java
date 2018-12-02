package com.lactaoen.ledger.controller.api;

import com.lactaoen.ledger.mapper.CasinoMapper;
import com.lactaoen.ledger.model.Casino;
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
@RequestMapping("/api/casino")
public class CasinoController extends AbstractApiController {

    private final CasinoMapper casinoMapper;

    public CasinoController(CasinoMapper casinoMapper) {
        this.casinoMapper = casinoMapper;
    }

    @GetMapping
    public List<Casino> getAllCasinos() {
        return casinoMapper.getAllCasinos();
    }

    @GetMapping("/{id}")
    public Casino getCasino(@PathVariable("id") int id) {
        return casinoMapper.getCasinoById(id);
    }

    @PostMapping
    public RedirectView createCasino(@ModelAttribute("casino") Casino casino, RedirectAttributes model) {
        generateFlashAttributes(model, casinoMapper.createCasino(casino), "casino", PostType.ADD);
        return new RedirectView("/form/casino");
    }

    @PostMapping("/{id}")
    public RedirectView updateCasino(@PathVariable("id") int id, @ModelAttribute("casino") Casino casino, RedirectAttributes model) {
        casino.setCasinoId(id);
        generateFlashAttributes(model, casinoMapper.updateCasino(casino), "casino", PostType.UPDATE);
        return new RedirectView("/");
    }

    @DeleteMapping("/{id}")
    public void deleteCasino(@PathVariable("id") int id) {
        casinoMapper.deleteCasino(id);
    }
}

package com.lactaoen.ledger.controller.api;

import com.lactaoen.ledger.mapper.CasinoMapper;
import com.lactaoen.ledger.model.Casino;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/casino")
public class CasinoController {

    private CasinoMapper casinoMapper;

    @RequestMapping(method = RequestMethod.GET)
    public List<Casino> getAllCasinos() {
        return casinoMapper.getAllCasinos();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Casino getCasino(@PathVariable("id") int id) {
        return casinoMapper.selectCasinoById(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public int createCasino(@ModelAttribute("casino") Casino casino) {
        return casinoMapper.createCasino(casino);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public int updateCasino(@PathVariable("id") int id, @RequestBody Casino casino) {
        return 1;
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

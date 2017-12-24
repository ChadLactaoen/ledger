package com.lactaoen.ledger.controller;

import com.lactaoen.ledger.model.Casino;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/casino")
public class CasinoController {

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Casino getCasino(@PathVariable("id") int id) {
        return null;
    }

    @RequestMapping(method = RequestMethod.POST)
    public int createCasino(@RequestBody Casino casino) {
        return 1;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public int updateCasino(@PathVariable("id") int id, @RequestBody Casino casino) {
        return 1;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteCasino(@PathVariable("id") int id) {

    }
}

package com.lactaoen.ledger.controller;

import com.lactaoen.ledger.model.Period;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/period")
public class PeriodController {

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Period getPeriod(@PathVariable("id") int id) {
        return null;
    }

    @RequestMapping(method = RequestMethod.POST)
    public int createPeriod(@RequestBody Period period) {
        return 1;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public int updatePeriod(@PathVariable("id") int id, @RequestBody Period period) {
        return 1;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deletePeriod(@PathVariable("id") int id) {

    }
}

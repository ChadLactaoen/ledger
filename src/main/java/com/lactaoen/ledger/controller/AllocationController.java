package com.lactaoen.ledger.controller;

import com.lactaoen.ledger.model.Allocation;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/allocation")
public class AllocationController {

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Allocation getAllocation(@PathVariable("id") int id) {
        return null;
    }

    @RequestMapping(method = RequestMethod.POST)
    public int createAllocation(@RequestBody Allocation allocation) {
        return 1;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public int updateAllocation(@PathVariable("id") int id, @RequestBody Allocation allocation) {
        return 1;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteAllocation(@PathVariable("id") int id) {

    }
}

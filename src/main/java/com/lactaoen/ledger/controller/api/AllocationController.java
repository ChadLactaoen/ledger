package com.lactaoen.ledger.controller.api;

import com.lactaoen.ledger.mapper.AllocationMapper;
import com.lactaoen.ledger.model.Allocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/allocation")
public class AllocationController {

    private AllocationMapper allocationMapper;

    @RequestMapping(method = RequestMethod.GET)
    public List<Allocation> getAllAllocations() {
        return allocationMapper.getAllAllocations();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Allocation getAllocation(@PathVariable("id") int id) {
        return allocationMapper.getAllocationById(id);
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

    @Autowired
    public void setAllocationMapper(AllocationMapper allocationMapper) {
        this.allocationMapper = allocationMapper;
    }
}

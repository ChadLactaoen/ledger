package com.lactaoen.ledger.controller.api;

import com.lactaoen.ledger.mapper.AllocationMapper;
import com.lactaoen.ledger.model.Allocation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/allocation")
public class AllocationController extends AbstractApiController {

    private final AllocationMapper allocationMapper;

    public AllocationController(AllocationMapper allocationMapper) {
        this.allocationMapper = allocationMapper;
    }

    @GetMapping("/period/{periodId}")
    public List<Allocation> getAllocationsByPeriodId(@PathVariable("periodId") int periodId) {
        return allocationMapper.getAllocationsByPeriodId(periodId);
    }
}

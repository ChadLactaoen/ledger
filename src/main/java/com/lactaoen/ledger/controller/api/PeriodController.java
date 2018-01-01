package com.lactaoen.ledger.controller.api;

import com.lactaoen.ledger.mapper.PeriodMapper;
import com.lactaoen.ledger.model.Period;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@RestController
@RequestMapping("/api/period")
public class PeriodController extends AbstractApiController {

    private PeriodMapper periodMapper;

    @RequestMapping(method = RequestMethod.GET)
    public List<Period> getAllPeriods() {
        return periodMapper.getAllPeriods();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Period getPeriod(@PathVariable("id") int id) {
        return periodMapper.getPeriodById(id);
    }

    @RequestMapping(value = "/last", method = RequestMethod.GET)
    public Period getLastPeriod() {
        Integer periodId = periodMapper.getLastPeriodId();
        return periodMapper.getPeriodById(periodId);
    }

    @RequestMapping(value = "/current", method = RequestMethod.GET)
    public Period getCurrentPeriod() {
        return periodMapper.getCurrentPeriod();
    }

    @RequestMapping(method = RequestMethod.POST)
    public RedirectView createPeriod(@RequestBody Period period, RedirectAttributes model) {
        return new RedirectView("/");
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public int updatePeriod(@PathVariable("id") int id, @RequestBody Period period) {
        return 1;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deletePeriod(@PathVariable("id") int id) {
        periodMapper.deletePeriod(id);
    }

    @Autowired
    public void setPeriodMapper(PeriodMapper periodMapper) {
        this.periodMapper = periodMapper;
    }
}

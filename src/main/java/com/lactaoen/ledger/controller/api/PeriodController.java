package com.lactaoen.ledger.controller.api;

import com.lactaoen.ledger.mapper.AllocationMapper;
import com.lactaoen.ledger.mapper.PeriodMapper;
import com.lactaoen.ledger.model.Period;
import com.lactaoen.ledger.model.form.AllocationForm;
import com.lactaoen.ledger.model.form.PeriodForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/period")
public class PeriodController extends AbstractApiController {

    private AllocationMapper allocationMapper;
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
    public RedirectView createPeriod(@ModelAttribute PeriodForm period, RedirectAttributes model) {

        if (isValidAmounts(period)) {
            int rowsInserted = periodMapper.createPeriod(period.toPeriod());

            if (rowsInserted != 0) {
                Integer periodId = periodMapper.getLastPeriodId();

                period.setPeriodId(periodId);
                List<AllocationForm> allocations = period.toAllocationForms();

                allocations.forEach(allocationMapper::createAllocation);
            }

            generateFlashAttributes(model, rowsInserted, "period", PostType.ADD);
        } else {
            model.addAttribute("msgClass", "danger");
            model.addAttribute("msg", "You must allocate the exact amount as given in the total");
        }

        return new RedirectView("/form/period");
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public RedirectView updatePeriod(@PathVariable("id") int id, @ModelAttribute PeriodForm period, RedirectAttributes model) {

        if (isValidAmounts(period)) {
            period.setPeriodId(id);
            periodMapper.updatePeriod(period.toPeriod());

            List<AllocationForm> allocations = period.toAllocationForms();
            allocations.stream().filter(allocation -> allocation.getTotal() != 1).forEach(allocationMapper::updateAllocation);

            allocations.stream().filter(allocation -> allocation.getTotal() == -1).forEach(allocation -> {
                Map<String, Integer> map = new HashMap<>();
                map.put("categoryId", allocation.getCategoryId());
                map.put("periodId", id);

                allocationMapper.deleteAllocation(map);
            });

            generateFlashAttributes(model, 1, "period", PostType.UPDATE);
        } else {
            model.addFlashAttribute("msgClass", "danger");
            model.addFlashAttribute("msg", "You must allocate the exact amount as given in the total");
            return new RedirectView("/form/period/" + id);
        }

        return new RedirectView("/");
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deletePeriod(@PathVariable("id") int id) {
        periodMapper.deletePeriod(id);
    }

    private boolean isValidAmounts(PeriodForm form) {
        double allocationSum = form.getAmounts().stream().filter(amount -> !amount.equals("-1")).mapToDouble(Double::parseDouble).sum();
        return allocationSum == form.getTotal();
    }

    @Autowired
    public void setAllocationMapper(AllocationMapper allocationMapper) {
        this.allocationMapper = allocationMapper;
    }

    @Autowired
    public void setPeriodMapper(PeriodMapper periodMapper) {
        this.periodMapper = periodMapper;
    }
}

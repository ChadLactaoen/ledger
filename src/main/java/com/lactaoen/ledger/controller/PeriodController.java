package com.lactaoen.ledger.controller;

import com.lactaoen.ledger.model.Allocation;
import com.lactaoen.ledger.model.Category;
import com.lactaoen.ledger.model.Period;
import com.lactaoen.ledger.model.form.PeriodForm;
import com.lactaoen.ledger.service.CategoryService;
import com.lactaoen.ledger.service.FlashAttributeService;
import com.lactaoen.ledger.service.PeriodService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.google.common.collect.ImmutableList.toImmutableList;
import static com.google.common.collect.ImmutableMap.toImmutableMap;
import static com.google.common.collect.ImmutableSet.toImmutableSet;

@RestController
@RequestMapping("period")
public class PeriodController {

    private final CategoryService categoryService;
    private final FlashAttributeService flashAttributeService;
    private final PeriodService periodService;

    public PeriodController(CategoryService categoryService, FlashAttributeService flashAttributeService, PeriodService periodService) {
        this.categoryService = categoryService;
        this.flashAttributeService = flashAttributeService;
        this.periodService = periodService;
    }

    @GetMapping
    public ModelAndView getPeriodForm(@RequestParam(value = "startDate", required = false) String startDate) {
        Period period = null;
        if (startDate != null) {
            period = periodService.getPeriodByDate(startDate);
        }
        List<Category> allCategories = categoryService.getAllChildCategories(CategoryService.SORT_BY_PARENT_THEN_NAME);

        ModelAndView mav = new ModelAndView("period");
        mav.addObject("periodForm", period == null ? createPreFilledForm(allCategories) : period.toForm());
        mav.addObject("allCategories", period == null ? allCategories : filterUnusedCategories(period, allCategories));

        if (period != null) {
            mav.addObject("spend", createSpendList(period));
        }

        return mav;
    }

    @PostMapping
    public RedirectView postPeriodForm(@ModelAttribute("period") PeriodForm periodForm, RedirectAttributes redirectAttributes) {
        boolean exists = periodService.getPeriodByDate(periodForm.getStartDate()) != null;

        FlashAttributeService.PostType postType  = exists ? FlashAttributeService.PostType.UPDATE : FlashAttributeService.PostType.ADD;
        periodService.savePeriod(periodForm);
        flashAttributeService.generateFlashAttributes(redirectAttributes, true, "period", postType);

        return new RedirectView("/");
    }

    private PeriodForm createPreFilledForm(List<Category> categories) {
        Period lastPeriod = periodService.getLatestPeriod();
        PeriodForm periodForm = new PeriodForm();
        periodForm.setTotal(lastPeriod.getTotal());
        periodForm.setAmounts(createSpendListForNewForm(lastPeriod, categories));
        periodForm.setCategories(categories.stream().map(Category::getName).collect(toImmutableList()));
        return periodForm;
    }

    private List<Category> filterUnusedCategories(Period period, List<Category> categories) {
        Set<String> usedCategories = period.getAllocations().stream().map(Allocation::getCategory).map(Category::getName).collect(toImmutableSet());
        return categories.stream().filter(category -> usedCategories.contains(category.getName())).collect(toImmutableList());
    }

    private List<Double> createSpendList(Period period) {
        return period.getAllocations().stream().map(Allocation::getSpent).collect(toImmutableList());
    }

    private List<String> createSpendListForNewForm(Period period, List<Category> categories) {
        Map<String, Double> allocationMap = period.getAllocations().stream().collect(toImmutableMap(allocation -> allocation.getCategory().getName(), Allocation::getTotal));
        return categories.stream().map(category -> Double.toString(allocationMap.getOrDefault(category.getName(), 0D))).collect(toImmutableList());
     }
}

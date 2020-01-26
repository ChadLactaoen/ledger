package com.lactaoen.ledger.controller;

import com.amazonaws.util.StringUtils;
import com.lactaoen.ledger.model.Transaction;
import com.lactaoen.ledger.model.form.TransactionForm;
import com.lactaoen.ledger.service.CategoryService;
import com.lactaoen.ledger.service.TransactionService;
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

@RestController
@RequestMapping("transaction")
public class TransactionController {

    private final TransactionService transactionService;
    private final CategoryService categoryService;
    private final PeriodService periodService;
    private final FlashAttributeService flashAttributeService;

    public TransactionController(TransactionService transactionService, CategoryService categoryService, PeriodService periodService, FlashAttributeService flashAttributeService) {
        this.transactionService = transactionService;
        this.categoryService = categoryService;
        this.periodService = periodService;
        this.flashAttributeService = flashAttributeService;
    }

    @GetMapping
    public ModelAndView getTransactionForm(@RequestParam(value = "transactionId", required = false) String transactionId) {
        Transaction transaction = null;
        if (transactionId != null) {
            transaction = transactionService.getTransactionById(transactionId);
        }

        ModelAndView mav = new ModelAndView("transaction");
        mav.addObject("transactionForm", transaction == null ? new TransactionForm() : transaction.toForm());
        mav.addObject("categories", categoryService.getAllChildCategories());

        return mav;
    }

    @PostMapping
    public RedirectView postTransactionForm(@ModelAttribute("transactionForm") TransactionForm transactionForm, RedirectAttributes redirectAttributes) {
        // We shouldn't add a transaction for a category that wasn't allocated for the period
        if (periodService.periodNotExistsForDate(transactionForm.getDate())) {
            redirectAttributes.addFlashAttribute("msgClass", "danger");
            redirectAttributes.addFlashAttribute("msg", "The transaction for " + transactionForm.getName()+ " was not added. " +
                    "Please add an allocation for its category to the respective period first.");

            return new RedirectView("transaction");
        }

        FlashAttributeService.PostType postType  = StringUtils.isNullOrEmpty(transactionForm.getTransactionId()) ? FlashAttributeService.PostType.ADD : FlashAttributeService.PostType.UPDATE;
        Transaction transaction = transactionService.convertFormToTransaction(transactionForm);
        transactionService.saveTransaction(transaction);
        boolean success = transactionService.getTransactionById(transaction.getTransactionId()) != null;

        flashAttributeService.generateFlashAttributes(redirectAttributes, success, "transaction", postType);

        return new RedirectView("transaction");
    }
}

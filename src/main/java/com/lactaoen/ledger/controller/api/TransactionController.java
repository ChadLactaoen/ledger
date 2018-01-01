package com.lactaoen.ledger.controller.api;

import com.lactaoen.ledger.mapper.AllocationMapper;
import com.lactaoen.ledger.mapper.TransactionMapper;
import com.lactaoen.ledger.model.Transaction;
import com.lactaoen.ledger.model.form.TransactionForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController extends AbstractApiController {

    private AllocationMapper allocationMapper;

    private TransactionMapper transactionMapper;

    @RequestMapping(method = RequestMethod.GET)
    public List<Transaction> getAllTransactions() {
        return transactionMapper.getAllTransactions();
    }

    @RequestMapping(value = "/year/{year}", method = RequestMethod.GET)
    public List<Transaction> getTransactionsByYear(@PathVariable("year") Integer year) {
        return transactionMapper.getTransactionsByYear(year);
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Transaction getTransaction(@PathVariable("id") int id) {
        return transactionMapper.getTransactionById(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public RedirectView createTransaction(@ModelAttribute("transaction") TransactionForm transaction, RedirectAttributes model) {

        // We shouldn't add a transaction for a category that wasn't allocated for the period
        if (allocationMapper.getAllocationByDateAndCategoryId(transaction.getDate(), transaction.getCategoryId()) == null) {
            model.addFlashAttribute("msgClass", "danger");
            model.addFlashAttribute("msg", "The transaction for " + transaction.getName()+ " was not added. " +
                    "Please add an allocation for its category to the respective period first.");

            return new RedirectView("/form/transaction");
        }

        generateFlashAttributes(model, transactionMapper.createTransaction(transaction), "transaction", PostType.ADD);
        return new RedirectView("/form/transaction");
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public RedirectView updateTransaction(@PathVariable("id") int id, @ModelAttribute("transaction") TransactionForm transaction, RedirectAttributes model) {

        // We shouldn't update a transaction for a category that wasn't allocated for the period
        if (allocationMapper.getAllocationByDateAndCategoryId(transaction.getDate(), transaction.getCategoryId()) == null) {
            model.addFlashAttribute("msgClass", "danger");
            model.addFlashAttribute("msg", "The transaction for " + transaction.getName()+ " was not updated. " +
                    "Please add an allocation for its category to the respective period first.");

            return new RedirectView("/form/transaction/" + id);
        }

        transaction.setTransactionId(id);
        generateFlashAttributes(model, transactionMapper.updateTransaction(transaction), "transaction", PostType.UPDATE);
        return new RedirectView("/");
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteTransaction(@PathVariable("id") int id) {
        transactionMapper.deleteTransaction(id);
    }

    @Autowired
    public void setAllocationMapper(AllocationMapper allocationMapper) {
        this.allocationMapper = allocationMapper;
    }

    @Autowired
    public void setTransactionMapper(TransactionMapper transactionMapper) {
        this.transactionMapper = transactionMapper;
    }
}

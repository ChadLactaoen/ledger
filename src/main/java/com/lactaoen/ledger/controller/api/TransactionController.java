package com.lactaoen.ledger.controller.api;

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
public class TransactionController {

    private TransactionMapper transactionMapper;

    @RequestMapping(method = RequestMethod.GET)
    public List<Transaction> getAllTransactions() {
        return transactionMapper.getAllTransactions();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Transaction getTransaction(@PathVariable("id") int id) {
        return transactionMapper.getTransactionById(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public RedirectView createTransaction(@ModelAttribute("transaction") TransactionForm transaction, RedirectAttributes model) {
        if (transactionMapper.createTransaction(transaction) != 0) {
            model.addFlashAttribute("msgClass", "success");
            model.addFlashAttribute("msg", "The transaction for " + transaction.getName() + " was added successfully");
        } else {
            model.addFlashAttribute("msgClass", "danger");
            model.addFlashAttribute("msg", "There was an issue adding the transaction for " + transaction.getName());
        }

        return new RedirectView("/");
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public RedirectView updateTransaction(@PathVariable("id") int id, @ModelAttribute("transaction") TransactionForm transaction, RedirectAttributes model) {
        transaction.setTransactionId(id);
        if (transactionMapper.updateTransaction(transaction) != 0) {
            model.addFlashAttribute("msgClass", "success");
            model.addFlashAttribute("msg", "The transaction for " + transaction.getName() + " was updated successfully");
        } else {
            model.addFlashAttribute("msgClass", "danger");
            model.addFlashAttribute("msg", "There was an issue updating the transaction for " + transaction.getName());
        }

        return new RedirectView("/");
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteTransaction(@PathVariable("id") int id) {
        transactionMapper.deleteTransaction(id);
    }

    @Autowired
    public void setTransactionMapper(TransactionMapper transactionMapper) {
        this.transactionMapper = transactionMapper;
    }
}

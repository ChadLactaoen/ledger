package com.lactaoen.ledger.controller.api;

import com.lactaoen.ledger.mapper.TransactionMapper;
import com.lactaoen.ledger.model.Transaction;
import com.lactaoen.ledger.model.form.TransactionForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public int createTransaction(@ModelAttribute("transaction") TransactionForm transaction) {
        return transactionMapper.createTransaction(transaction);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public int updateTransaction(@PathVariable("id") int id, @RequestBody Transaction transaction) {
        return 1;
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

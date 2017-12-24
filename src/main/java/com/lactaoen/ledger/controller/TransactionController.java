package com.lactaoen.ledger.controller;

import com.lactaoen.ledger.model.Transaction;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Transaction getTransaction(@PathVariable("id") int id) {
        return null;
    }

    @RequestMapping(method = RequestMethod.POST)
    public int createTransaction(@RequestBody Transaction transaction) {
        return 1;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public int updateTransaction(@PathVariable("id") int id, @RequestBody Transaction transaction) {
        return 1;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteTransaction(@PathVariable("id") int id) {

    }
}

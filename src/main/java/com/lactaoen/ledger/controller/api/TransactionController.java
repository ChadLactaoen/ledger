package com.lactaoen.ledger.controller.api;

import com.lactaoen.ledger.mapper.AllocationMapper;
import com.lactaoen.ledger.mapper.TransactionMapper;
import com.lactaoen.ledger.model.Transaction;
import com.lactaoen.ledger.model.form.TransactionForm;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController extends AbstractApiController {

    private final AllocationMapper allocationMapper;
    private final TransactionMapper transactionMapper;

    public TransactionController(AllocationMapper allocationMapper, TransactionMapper transactionMapper) {
        this.allocationMapper = allocationMapper;
        this.transactionMapper = transactionMapper;
    }

    @GetMapping
    public List<Transaction> getAllTransactions() {
        return transactionMapper.getAllTransactions();
    }

    @GetMapping("/year/{year}")
    public List<Transaction> getTransactionsByYear(@PathVariable("year") Integer year) {
        return transactionMapper.getTransactionsByYear(year);
    }


    @GetMapping("/{id}")
    public Transaction getTransaction(@PathVariable("id") int id) {
        return transactionMapper.getTransactionById(id);
    }

    @PostMapping
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

    @PostMapping("/{id}")
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

    @DeleteMapping("/{id}")
    public void deleteTransaction(@PathVariable("id") int id) {
        transactionMapper.deleteTransaction(id);
    }
}

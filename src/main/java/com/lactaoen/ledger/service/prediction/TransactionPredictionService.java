package com.lactaoen.ledger.service.prediction;

import com.lactaoen.ledger.model.Transaction;
import com.lactaoen.ledger.service.TransactionService;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Service
public class TransactionPredictionService  {

    private static final double ACCEPTABLE_THRESHOLD = 0.9;
    private static final Function<Transaction, String> MAP_TO_CATEGORY_NAME = transaction -> transaction.getCategory().getName();

    private final TransactionService transactionService;

    public TransactionPredictionService(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    public String predictCategory(String name) {
        List<Transaction> matchedTransactions = transactionService.getAllTransactionByName(name);
        int totalTransactions = matchedTransactions.size();

        if (totalTransactions == 0) {
            return null;
        }

        Map.Entry<String, Long> modeEntry = getMode(matchedTransactions);
        return (modeEntry.getValue() / (double) totalTransactions) >= ACCEPTABLE_THRESHOLD ? modeEntry.getKey() : null;
    }

    private Map.Entry<String, Long> getMode(List<Transaction> transactions) {
        return transactions.stream()
                .collect(groupingBy(MAP_TO_CATEGORY_NAME, Collectors.counting()))
                .entrySet()
                .stream()
                .max(Comparator.comparing(Map.Entry::getValue))
                .get();
    }
}

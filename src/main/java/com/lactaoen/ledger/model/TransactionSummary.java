package com.lactaoen.ledger.model;

import java.util.List;

public class TransactionSummary {

    private final String name;
    private final Category category;
    private final Double price;
    private final int count;
    private final List<Transaction> transactions;

    public TransactionSummary(List<Transaction> transactions) {
        this.transactions = transactions;
        Transaction transaction = transactions.get(0);
        count = transactions.size();
        price = transactions.stream().mapToDouble(this::getTruePrice).sum();
        category = transaction.getSubcategory() == null ? transaction.getCategory() : transaction.getSubcategory();
        name = transaction.getName();
    }

    public String getName() {
        return name;
    }

    public Category getCategory() {
        return category;
    }

    public Double getPrice() {
        return price;
    }

    public int getCount() {
        return count;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public Double getAverage() {
        return price / count;
    }

    private Double getTruePrice(Transaction transaction) {
        return transaction.getSubcategory() == null ? transaction.getPrice() : Math.abs(transaction.getPrice());
    }
}

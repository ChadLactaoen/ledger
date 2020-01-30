package com.lactaoen.ledger.model.form;

import com.amazonaws.util.StringUtils;
import com.lactaoen.ledger.model.Transaction;

public class TransactionForm {

    private String transactionId;
    private String category;
    private String subcategory;
    private String name;
    private Double price;
    private String memo;
    private String date;

    public TransactionForm() {
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Transaction toTransaction() {
        Transaction transaction = new Transaction();
        transaction.setTransactionId(StringUtils.isNullOrEmpty(transactionId) ? transaction.getTransactionId() : transactionId);
        transaction.setName(name);
        transaction.setPrice(price);
        transaction.setDate(date);
        transaction.setMemo(memo);

        String[] dateParts = date.split("-");
        dateParts[2] = "01";
        transaction.setEffectivePeriod(String.join("-", dateParts));
        transaction.setEffectiveYear(dateParts[0]);
        return transaction;
    }
}

package com.lactaoen.ledger.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lactaoen.ledger.model.form.TransactionForm;

import java.sql.Date;

public class Transaction {

    private Integer transactionId;
    private Date date;
    private String name;
    private Double price;
    private String memo;
    private Category category;
    private Category subcategory;

    public Transaction() {
    }

    public Integer getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Integer transactionId) {
        this.transactionId = transactionId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Category getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(Category subcategory) {
        this.subcategory = subcategory;
    }

    public boolean isReimbursement() {
        return memo.toLowerCase().contains("reimburse");
    }

    public Transaction copy() {
        Transaction transaction = new Transaction();
        transaction.setTransactionId(transactionId);
        transaction.setDate(date);
        transaction.setName(name);
        transaction.setPrice(price);
        transaction.setMemo(memo);
        transaction.setCategory(category);
        transaction.setSubcategory(subcategory);
        return transaction;
    }

    @JsonIgnore
    public TransactionForm toTransactionForm() {
        TransactionForm form = new TransactionForm();
        form.setTransactionId(transactionId);
        form.setDate(date);
        form.setName(name);
        form.setPrice(price);
        form.setMemo(memo);
        form.setCategoryId(category.getCategoryId());
        if (subcategory != null) {
            form.setSubcategoryId(subcategory.getCategoryId());
        }
        return form;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId=" + transactionId +
                ", date=" + date +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", memo='" + memo + '\'' +
                ", category=" + category +
                ", subcategory=" + subcategory +
                '}';
    }
}

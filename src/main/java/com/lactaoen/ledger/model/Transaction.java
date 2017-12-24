package com.lactaoen.ledger.model;

import java.sql.Date;

public class Transaction {

    private int transactionId;
    private Date date;
    private String name;
    private Double price;
    private String memo;
    private Category category;

    public Transaction() {
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
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

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId=" + transactionId +
                ", date=" + date +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", memo='" + memo + '\'' +
                ", category=" + category +
                '}';
    }
}

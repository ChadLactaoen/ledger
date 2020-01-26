package com.lactaoen.ledger.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIgnore;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.lactaoen.ledger.model.form.TransactionForm;

@DynamoDBTable(tableName = "Transaction")
public class Transaction {

    private String transactionId;
    private Category category;
    private Category subcategory;
    private String name;
    private Double price;
    private String memo;
    private String date;

    public Transaction() {
    }

    @DynamoDBHashKey
    @DynamoDBAutoGeneratedKey
    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    @DynamoDBAttribute
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @DynamoDBAttribute
    public Category getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(Category subcategory) {
        this.subcategory = subcategory;
    }

    @DynamoDBAttribute
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @DynamoDBAttribute
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @DynamoDBAttribute
    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @DynamoDBAttribute
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @DynamoDBIgnore
    public boolean isReimbursement() {
        return memo != null && memo.toLowerCase().contains("reimburse");
    }

    @DynamoDBIgnore
    public TransactionForm toForm() {
        TransactionForm transactionForm = new TransactionForm();
        transactionForm.setTransactionId(transactionId);
        transactionForm.setCategory(category.getName());
        if (subcategory != null) {
            transactionForm.setSubcategory(subcategory.getName());
        }
        transactionForm.setName(name);
        transactionForm.setPrice(price);
        transactionForm.setMemo(memo);
        transactionForm.setDate(date);
        return transactionForm;
    }

    @Override
    public String toString() {
        return "{" +
                "transactionId='" + transactionId + '\'' +
                ", category=" + category +
                ", subcategory=" + subcategory +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", memo='" + memo + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}

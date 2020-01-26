package com.lactaoen.ledger.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIgnore;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.google.common.collect.ImmutableList;
import com.lactaoen.ledger.model.form.PeriodForm;

import java.util.List;

@DynamoDBTable(tableName = "Period")
public class Period {

    private String startDate;
    private String endDate;
    private Double total;
    private List<Allocation> allocations;
    private List<Transaction> transactions;

    public Period() {
    }

    @DynamoDBHashKey
    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    @DynamoDBAttribute
    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    @DynamoDBAttribute
    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    @DynamoDBAttribute
    public List<Allocation> getAllocations() {
        return allocations;
    }

    public void setAllocations(List<Allocation> allocations) {
        this.allocations = allocations;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    @DynamoDBIgnore
    public Double getRemaining() {
        return total - transactions.stream().mapToDouble(Transaction::getPrice).sum();
    }

    @DynamoDBIgnore
    public PeriodForm toForm() {
        PeriodForm periodForm = new PeriodForm();
        periodForm.setStartDate(startDate);
        periodForm.setEndDate(endDate);
        periodForm.setTotal(total);
        ImmutableList.Builder<String> categoryListBuilder = new ImmutableList.Builder<>();
        ImmutableList.Builder<String> amountListBuilder = new ImmutableList.Builder<>();
        allocations.forEach(allocation -> {
           categoryListBuilder.add(allocation.getCategory().getName());
           amountListBuilder.add(String.valueOf(allocation.getTotal()));
        });
        periodForm.setCategories(categoryListBuilder.build());
        periodForm.setAmounts(amountListBuilder.build());
        return periodForm;
    }
}

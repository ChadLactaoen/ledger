package com.lactaoen.ledger.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIgnore;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.google.common.collect.ImmutableList;
import com.lactaoen.ledger.model.form.PeriodForm;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static com.google.common.collect.ImmutableList.toImmutableList;
import static java.util.stream.Collectors.groupingBy;

@DynamoDBTable(tableName = "Period")
public class Period {

    private String startDate;
    private String endDate;
    private Double total;
    private String notes;
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
    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
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
        return total
                - transactions
                        .stream()
                        .filter(t -> !("Rainy Day".equals(t.getCategory().getName()) && t.getPrice() < 0))
                        .mapToDouble(Transaction::getPrice)
                        .sum();
    }

    @DynamoDBIgnore
    public PeriodForm toForm() {
        PeriodForm periodForm = new PeriodForm();
        periodForm.setStartDate(startDate);
        periodForm.setEndDate(endDate);
        periodForm.setTotal(total);
        periodForm.setNotes(notes);
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

    @DynamoDBIgnore
    public List<Allocation> getAllocationsByParentCategory() {
        Map<String, List<Allocation>> parentAllocations = allocations
                .stream()
                .collect(groupingBy(allocation -> allocation.getCategory().getParent()));

        return parentAllocations
                .entrySet()
                .stream()
                .map(this::toParentAllocation)
                .sorted(Comparator.comparing(allocation -> allocation.getCategory().getName()))
                .collect(toImmutableList());

    }

    private Allocation toParentAllocation(Map.Entry<String, List<Allocation>> entry) {
        List<Allocation> subAllocations = entry.getValue();

        int count = 0;
        Double spent = 0d;
        Double total = 0d;

        for (Allocation subAllocation : subAllocations) {
            count += subAllocation.getCount();
            spent += subAllocation.getSpent() != null ? subAllocation.getSpent() : 0;
            total += subAllocation.getTotal() != null ? subAllocation.getTotal() : 0;
        }

        Allocation allocation = new Allocation();
        allocation.setCount(count);
        allocation.setTotal(total);
        allocation.setSpent(spent);

        Category category = new Category();
        category.setName(entry.getKey());
        category.setColor(subAllocations.get(0).getCategory().getColor());
        allocation.setCategory(category);
        return allocation;
    }
}

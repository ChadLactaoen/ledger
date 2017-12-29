package com.lactaoen.ledger.model;

import java.util.List;

public class Allocation {

    private Integer allocationId;
    private Category category;
    private Period period;
    private Double total;
    private List<Transaction> transactionList;

    public Allocation() {
    }

    public Integer getAllocationId() {
        return allocationId;
    }

    public void setAllocationId(Integer allocationId) {
        this.allocationId = allocationId;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Period getPeriod() {
        return period;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public List<Transaction> getTransactionList() {
        return transactionList;
    }

    public void setTransactionList(List<Transaction> transactionList) {
        this.transactionList = transactionList;
    }

    public double getSpent() {
        if (transactionList == null || transactionList.isEmpty()) {
            return 0D;
        }

        return transactionList.stream().mapToDouble(Transaction::getPrice).sum();
    }

    @Override
    public String toString() {
        return "Allocation{" +
                "allocationId=" + allocationId +
                ", category=" + category +
                ", period=" + period +
                ", total=" + total +
                ", transactionList=" + transactionList +
                '}';
    }
}

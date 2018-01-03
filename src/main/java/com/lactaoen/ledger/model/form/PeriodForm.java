package com.lactaoen.ledger.model.form;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lactaoen.ledger.model.Period;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class PeriodForm {

    private Integer periodId;
    private Date startDate;
    private Date endDate;
    private Double total;
    private List<String> categoryIds;
    private List<String> amounts;

    public PeriodForm() {
    }

    public Integer getPeriodId() {
        return periodId;
    }

    public void setPeriodId(Integer periodId) {
        this.periodId = periodId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public List<String> getCategoryIds() {
        return categoryIds;
    }

    public void setCategoryIds(List<String> categoryIds) {
        this.categoryIds = categoryIds;
    }

    public List<String> getAmounts() {
        return amounts;
    }

    public void setAmounts(List<String> amounts) {
        this.amounts = amounts;
    }

    @JsonIgnore
    public Period toPeriod() {
        Period period = new Period();
        period.setPeriodId(periodId);
        period.setStartDate(startDate);
        period.setEndDate(endDate);
        period.setTotal(total);
        return period;
    }

    @JsonIgnore
    public List<AllocationForm> toAllocationForms() {
        List<AllocationForm> allocations = new ArrayList<>();

        for (int i = 0; i < amounts.size(); i++) {
            AllocationForm allocation = new AllocationForm();
            Integer categoryId = Integer.parseInt(categoryIds.get(i));
            Double amount = Double.parseDouble(amounts.get(i));

            allocation.setPeriodId(periodId);
            allocation.setCategoryId(categoryId);
            allocation.setTotal(amount);
            allocations.add(allocation);
        }

        return allocations;
    }

    @Override
    public String toString() {
        return "PeriodForm{" +
                "periodId=" + periodId +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", total=" + total +
                ", categoryIds=" + categoryIds +
                ", amounts=" + amounts +
                '}';
    }
}

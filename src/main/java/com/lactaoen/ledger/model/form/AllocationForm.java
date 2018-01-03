package com.lactaoen.ledger.model.form;

public class AllocationForm {

    private Integer allocationId;
    private Integer categoryId;
    private Integer periodId;
    private Double total;

    public AllocationForm() {
    }

    public Integer getAllocationId() {
        return allocationId;
    }

    public void setAllocationId(Integer allocationId) {
        this.allocationId = allocationId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getPeriodId() {
        return periodId;
    }

    public void setPeriodId(Integer periodId) {
        this.periodId = periodId;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}

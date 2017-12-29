package com.lactaoen.ledger.model;

import java.sql.Date;
import java.util.List;
import java.util.Map;

public class Period {

    private Integer periodId;
    private Date startDate;
    private Date endDate;
    private Double total;
    private List<Allocation> allocationList;

    public Period() {
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

    public List<Allocation> getAllocationList() {
        return allocationList;
    }

    public void setAllocationList(List<Allocation> allocationList) {
        this.allocationList = allocationList;
    }

    public double getSpent() {
        if (allocationList == null || allocationList.isEmpty()) {
            return 0D;
        }

        return allocationList.stream().mapToDouble(Allocation::getSpent).sum();
    }

    @Override
    public String toString() {
        return "Period{" +
                "periodId=" + periodId +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", total=" + total +
                ", allocationList=" + allocationList +
                '}';
    }
}

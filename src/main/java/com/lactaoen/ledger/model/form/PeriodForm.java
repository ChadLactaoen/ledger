package com.lactaoen.ledger.model.form;

import com.lactaoen.ledger.model.Period;

import java.util.List;

public class PeriodForm {

    private String startDate;
    private Double total;
    private String notes;
    private List<String> categories;
    private List<String> amounts;

    public PeriodForm() {
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public List<String> getAmounts() {
        return amounts;
    }

    public void setAmounts(List<String> amounts) {
        this.amounts = amounts;
    }

    public Period toPeriod() {
        Period period = new Period();
        period.setStartDate(startDate);
        period.setTotal(total);
        period.setNotes(notes);
        return period;
    }
}

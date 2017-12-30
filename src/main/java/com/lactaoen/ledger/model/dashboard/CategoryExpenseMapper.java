package com.lactaoen.ledger.model.dashboard;

public class CategoryExpenseMapper {

    private Integer categoryId;
    private String categoryName;
    private String parentCategoryName;
    private Integer transactionCount;
    private Double total;
    private Double averageSpent;

    public CategoryExpenseMapper() {
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getParentCategoryName() {
        return parentCategoryName;
    }

    public void setParentCategoryName(String parentCategoryName) {
        this.parentCategoryName = parentCategoryName;
    }

    public Integer getTransactionCount() {
        return transactionCount;
    }

    public void setTransactionCount(Integer transactionCount) {
        this.transactionCount = transactionCount;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getAverageSpent() {
        return averageSpent;
    }

    public void setAverageSpent(Double averageSpent) {
        this.averageSpent = averageSpent;
    }
}

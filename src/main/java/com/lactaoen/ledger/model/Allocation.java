package com.lactaoen.ledger.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIgnore;

import java.math.BigDecimal;
import java.math.RoundingMode;

@DynamoDBDocument
public class Allocation {

    private Category category;
    private Double total;
    private Double spent;
    private int count;

    public Allocation() {
    }

    @DynamoDBAttribute
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @DynamoDBAttribute
    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getSpent() {
        return spent;
    }

    public void setSpent(Double spent) {
        this.spent = spent;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @DynamoDBIgnore
    public Double getRemaining() {
        return new BigDecimal(total - spent).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}

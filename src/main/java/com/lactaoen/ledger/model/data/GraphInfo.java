package com.lactaoen.ledger.model.data;

import com.lactaoen.ledger.model.Category;

public class GraphInfo {

    private final Category category;
    private final double total;

    public GraphInfo(Category category, double total) {
        this.category = category;
        this.total = total;
    }

    public Category getCategory() {
        return category;
    }

    public double getTotal() {
        return total;
    }
}

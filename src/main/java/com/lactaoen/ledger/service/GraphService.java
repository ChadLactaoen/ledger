package com.lactaoen.ledger.service;

import com.lactaoen.ledger.model.Allocation;
import com.lactaoen.ledger.model.Category;
import com.lactaoen.ledger.model.data.GraphInfo;
import com.lactaoen.ledger.model.Period;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static com.google.common.collect.ImmutableList.toImmutableList;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingDouble;

@Service
public class GraphService {

    public GraphService() {
    }

    public List<GraphInfo> getGraphData(Period period) {
        Map<Category, Double> allocationMap = period.getAllocations().stream()
                .collect(groupingBy(this::createParentCategory, summingDouble(Allocation::getTotal)));
        return allocationMap.entrySet().stream().map(entry -> new GraphInfo(entry.getKey(), entry.getValue())).collect(toImmutableList());
    }

    private Category createParentCategory(Allocation allocation) {
        Category category = allocation.getCategory();
        Category parentCategory = new Category();
        parentCategory.setName(category.getParent());
        parentCategory.setColor(category.getColor());
        parentCategory.setParent("None");
        return parentCategory;
    }
}

package com.lactaoen.ledger.service;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.google.common.collect.ImmutableMap;
import com.lactaoen.ledger.model.Category;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

import static com.google.common.collect.ImmutableList.toImmutableList;

@Service
public class CategoryService {

    private static final Comparator<Category> BY_PARENT = Comparator.comparing(Category::getParent);
    private static final Comparator<Category> BY_NAME = Comparator.comparing(Category::getName);

    private final DynamoDBMapper dynamoDBMapper;

    public CategoryService(DynamoDBMapper dynamoDBMapper) {
        this.dynamoDBMapper = dynamoDBMapper;
    }

    public List<Category> getAllChildCategories() {
        DynamoDBScanExpression expression = new DynamoDBScanExpression()
                .withFilterExpression("#parent <> :none")
                .withExpressionAttributeNames(ImmutableMap.of("#parent", "parent"))
                .withExpressionAttributeValues(ImmutableMap.of(":none", new AttributeValue("None")));

        List<Category> categories = dynamoDBMapper.scan(Category.class, expression);
        return categories.stream().sorted(BY_PARENT.thenComparing(BY_NAME)).collect(toImmutableList());
    }

    public Category getCategoryByName(String name) {
        Category category = new Category();
        category.setName(name);

        return dynamoDBMapper.query(Category.class, new DynamoDBQueryExpression<Category>().withHashKeyValues(category)).get(0);
    }
}

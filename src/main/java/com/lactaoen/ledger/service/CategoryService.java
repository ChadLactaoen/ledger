package com.lactaoen.ledger.service;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.google.common.collect.ImmutableMap;
import com.lactaoen.ledger.model.Category;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

import static com.google.common.collect.ImmutableList.toImmutableList;
import static java.util.function.Predicate.not;

@Service
public class CategoryService {

    private static final Comparator<Category> BY_PARENT = Comparator.comparing(Category::getParent);
    public static final Comparator<Category> BY_NAME = Comparator.comparing(Category::getName);
    public static final Comparator<Category> SORT_BY_PARENT_THEN_NAME = BY_PARENT.thenComparing(BY_NAME);

    private final DynamoDBMapper dynamoDBMapper;

    public CategoryService(DynamoDBMapper dynamoDBMapper) {
        this.dynamoDBMapper = dynamoDBMapper;
    }

    public List<Category> getAllChildCategories(Comparator<Category> sortMethod) {
        DynamoDBScanExpression expression = new DynamoDBScanExpression()
                .withFilterExpression("#parent <> :none")
                .withExpressionAttributeNames(ImmutableMap.of("#parent", "parent"))
                .withExpressionAttributeValues(ImmutableMap.of(":none", new AttributeValue("None")));

        List<Category> categories = dynamoDBMapper.scan(Category.class, expression);
        return categories.stream()
                .filter(cat -> cat.isDeprecated() == null || !cat.isDeprecated())
                .sorted(sortMethod)
                .collect(toImmutableList());
    }

    public Category getCategoryByName(String name) {
        Category category = new Category();
        category.setName(name);

        DynamoDBScanExpression expression = new DynamoDBScanExpression()
                .withFilterExpression("#name = :name AND #parent <> :none")
                .withExpressionAttributeNames(ImmutableMap.of("#name", "name", "#parent", "parent"))
                .withExpressionAttributeValues(ImmutableMap.of(":name", new AttributeValue().withS(name), ":none", new AttributeValue().withS("None")));

        return dynamoDBMapper.scan(Category.class, expression).get(0);
    }
}

package com.lactaoen.ledger.service;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.util.StringUtils;
import com.google.common.collect.ImmutableMap;
import com.lactaoen.ledger.model.Transaction;
import com.lactaoen.ledger.model.form.TransactionForm;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    private final DynamoDBMapper dynamoDBMapper;
    private final CategoryService categoryService;

    public TransactionService(DynamoDBMapper dynamoDBMapper, CategoryService categoryService) {
        this.dynamoDBMapper = dynamoDBMapper;
        this.categoryService = categoryService;
    }

    public Transaction getTransactionById(String transactionId) {
        return dynamoDBMapper.load(Transaction.class, transactionId);
    }

    public List<Transaction> getAllTransactionByName(String name) {
        return dynamoDBMapper.query(Transaction.class, createNameIndexQuery(name));
    }

    public List<Transaction> getTransactionsForMonth(String startDate) {
        return dynamoDBMapper.query(Transaction.class, createEffectivePeriodIndexQuery(startDate));
    }

    public List<Transaction> getTransactionsByYear(int year) {
        return dynamoDBMapper.query(Transaction.class, createEffectiveYearIndexQuery(String.valueOf(year)));
    }

    public void saveTransaction(Transaction transaction) {
        dynamoDBMapper.save(transaction);
    }

    public Transaction convertFormToTransaction(TransactionForm transactionForm) {
        Transaction transaction = transactionForm.toTransaction();
        transaction.setCategory(categoryService.getCategoryByName(transactionForm.getCategory()));

        if (!StringUtils.isNullOrEmpty(transactionForm.getSubcategory())) {
            transaction.setSubcategory(categoryService.getCategoryByName(transactionForm.getSubcategory()));
        }

        return transaction;
    }

    private DynamoDBScanExpression createScanExpression(String startDate, String endDate) {
        return new DynamoDBScanExpression()
                .withFilterExpression("#date BETWEEN :startDate and :endDate")
                .withExpressionAttributeNames(ImmutableMap.of("#date", "date"))
                .withExpressionAttributeValues(ImmutableMap.of(":startDate", new AttributeValue(startDate), ":endDate", new AttributeValue(endDate)));
    }

    private DynamoDBQueryExpression<Transaction> createNameIndexQuery(String name) {
        return new DynamoDBQueryExpression<Transaction>()
                .withIndexName("name-date-index")
                .withConsistentRead(false)
                .withKeyConditionExpression("#name = :name")
                .withExpressionAttributeNames(ImmutableMap.of("#name", "name"))
                .withExpressionAttributeValues(ImmutableMap.of(":name", new AttributeValue(name)))
                .withProjectionExpression("category");
    }

    private DynamoDBQueryExpression<Transaction> createEffectivePeriodIndexQuery(String year) {
        return new DynamoDBQueryExpression<Transaction>()
                .withIndexName("effectivePeriod-transactionId-index")
                .withConsistentRead(false)
                .withKeyConditionExpression("effectivePeriod = :year")
                .withExpressionAttributeValues(ImmutableMap.of(":year", new AttributeValue(year)));
    }

    private DynamoDBQueryExpression<Transaction> createEffectiveYearIndexQuery(String year) {
        return new DynamoDBQueryExpression<Transaction>()
                .withIndexName("effectiveYear-transactionId-index")
                .withConsistentRead(false)
                .withKeyConditionExpression("effectiveYear = :year")
                .withExpressionAttributeValues(ImmutableMap.of(":year", new AttributeValue(year)));
    }
}

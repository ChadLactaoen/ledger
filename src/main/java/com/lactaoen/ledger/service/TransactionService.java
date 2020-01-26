package com.lactaoen.ledger.service;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.util.StringUtils;
import com.google.common.collect.ImmutableMap;
import com.lactaoen.ledger.model.Transaction;
import com.lactaoen.ledger.model.form.TransactionForm;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    public List<Transaction> getTransactionsForMonth(String startDate, String endDate) {
        return dynamoDBMapper.scan(Transaction.class, createScanExpression(startDate, endDate));
    }

    public List<Transaction> getTransactionsByYear(int year) {
        String startDate = LocalDate.of(year, 1, 1).toString();
        String endDate = LocalDate.of(year, 12, 31).toString();
        return dynamoDBMapper.scan(Transaction.class, createScanExpression(startDate, endDate));
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
}

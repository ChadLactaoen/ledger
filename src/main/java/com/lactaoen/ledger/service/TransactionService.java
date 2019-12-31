package com.lactaoen.ledger.service;

import com.lactaoen.ledger.model.Category;
import com.lactaoen.ledger.model.Transaction;
import com.lactaoen.ledger.model.dashboard.CategoryExpenseMapper;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

@Service
public class TransactionService {

    private static final int OTHER_CATEGORY_ID = 63;
    private static final Comparator<CategoryExpenseMapper> BY_PARENT_CATEGORY = Comparator.comparing(expense -> expense.getParentCategory().getCategoryId());
    private static final Comparator<CategoryExpenseMapper> BY_CATEGORY_NAME = Comparator.comparing(CategoryExpenseMapper::getCategoryName);

    public List<CategoryExpenseMapper> groupTransactionsByCategory(List<Transaction> transactions) {
        Map<Category, List<Transaction>> transactionsByCategory = transactions.stream().filter(transaction -> transaction.getPrice() >= 0).collect(groupingBy(Transaction::getCategory));
        List<Transaction> transactionsLessThanZero = transactions.stream().filter(transaction -> transaction.getPrice() < 0).collect(toList());

        addTransactionsToProperCategory(transactionsByCategory, transactionsLessThanZero);
        return transactionsByCategory.entrySet()
                .stream()
                .map(this::createCategoryExpense)
                .sorted(BY_PARENT_CATEGORY.thenComparing(BY_CATEGORY_NAME))
                .collect(toList());
    }

    private void addTransactionsToProperCategory(Map<Category, List<Transaction>> transactionsByCategory, List<Transaction> transactions) {
        Category otherCategory = transactionsByCategory.keySet().stream().filter(category -> category.getCategoryId() == OTHER_CATEGORY_ID).findFirst().orElse(transactionsByCategory.keySet().iterator().next());
        for (Transaction transaction : transactions) {
            if (transaction.isReimbursement()) {
                transactionsByCategory.get(transaction.getCategory()).add(transaction);
            } else {
                transactionsByCategory.get(transaction.getCategory()).add(transaction);

                Double absolutePrice = Math.abs(transaction.getPrice());
                Transaction transactionCopy = transaction.copy();
                transactionCopy.setPrice(absolutePrice);

                if (transaction.getSubcategory() == null) {
                    transactionsByCategory.get(otherCategory).add(transactionCopy);
                } else {
                    transactionsByCategory.get(transactionCopy.getSubcategory()).add(transactionCopy);
                }
            }
        }
    }

    private CategoryExpenseMapper createCategoryExpense(Map.Entry<Category, List<Transaction>> categoryListEntry) {
        Category category = categoryListEntry.getKey();
        List<Transaction> transactions = categoryListEntry.getValue();
        CategoryExpenseMapper expenseMapper = new CategoryExpenseMapper();
        expenseMapper.setCategoryId(category.getCategoryId());
        expenseMapper.setCategoryName(category.getName());
        expenseMapper.setParentCategory(category.getParentCategory());
        expenseMapper.setTotal(transactions.stream().mapToDouble(Transaction::getPrice).sum());
        expenseMapper.setTransactionCount((int) transactions.stream().filter(transaction -> transaction.getPrice() > 0).count());
        expenseMapper.setAverageSpent(expenseMapper.getTotal() / expenseMapper.getTransactionCount());
        return expenseMapper;
    }
}

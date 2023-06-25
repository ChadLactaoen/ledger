package com.lactaoen.ledger.service;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.google.common.collect.ImmutableList;
import com.lactaoen.ledger.model.Allocation;
import com.lactaoen.ledger.model.Category;
import com.lactaoen.ledger.model.Period;
import com.lactaoen.ledger.model.Transaction;
import com.lactaoen.ledger.model.form.PeriodForm;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import static com.google.common.collect.ImmutableList.toImmutableList;
import static com.google.common.collect.ImmutableSet.toImmutableSet;
import static java.util.stream.Collectors.groupingBy;

@Service
public class PeriodService {

    private static final Comparator<Allocation> BY_PARENT_CATEGORY = Comparator.comparing(allocation -> allocation.getCategory().getParent());
    private static final Comparator<Allocation> BY_CATEGORY_NAME = Comparator.comparing(allocation -> allocation.getCategory().getName());
    private static final Comparator<Transaction> BY_NEWEST = Comparator.comparing(Transaction::getDate).reversed();
    private static final SimpleDateFormat DYNAMO_FORMAT = new SimpleDateFormat("yyyy-MM");
    private static final Function<String, Date> BY_DATE_ASC = date -> {
        try {
            return DYNAMO_FORMAT.parse(date);
        } catch (ParseException e) {
            return new Date();
        }
    };

    private final AmazonDynamoDB amazonDynamoDB;
    private final DynamoDBMapper dynamoDBMapper;
    private final TransactionService transactionService;
    private final CategoryService categoryService;

    public PeriodService(AmazonDynamoDB amazonDynamoDB, DynamoDBMapper dynamoDBMapper, TransactionService transactionService, CategoryService categoryService) {
        this.amazonDynamoDB = amazonDynamoDB;
        this.dynamoDBMapper = dynamoDBMapper;
        this.transactionService = transactionService;
        this.categoryService = categoryService;
    }

    public Period getPeriodByDate(String date) {
        Period period = dynamoDBMapper.load(Period.class, date);

        if (period == null) {
            return null;
        }
        period.setTransactions(transactionService.getTransactionsForMonth(period.getStartDate()));

        populateWithTransactions(period);
        populateSpendByCategory(period);
        sortAllocations(period);

        return period;
    }

    public Period getPeriodByYear(int year) {
        List<Transaction> transactions = transactionService.getTransactionsByYear(year).stream().sorted(BY_NEWEST).collect(toImmutableList());
        Map<Category, List<Transaction>> transactionMap = transactions.stream()
                    .collect(groupingBy(Transaction::getCategory));
        Map<Category, List<Transaction>> subcategoryTransactionMap = transactions
                .stream()
                .filter(transaction -> transaction.getSubcategory() != null)
                .collect(groupingBy(Transaction::getSubcategory));
        List<Allocation> allocations = convertToAllocations(transactionMap, subcategoryTransactionMap);

        Period period = new Period();
        period.setTotal(allocations.stream().mapToDouble(Allocation::getTotal).sum());
        period.setStartDate(year + "-01");
        period.setAllocations(allocations);
        period.setTransactions(transactions);

        sortAllocations(period);
        populateWithTransactions(period);

        return period;
    }

    public Period getLatestPeriod() {
        YearMonth now = YearMonth.now(ZoneId.systemDefault());
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM");

        Period period = getPeriodByDate(fmt.format(now));
        while (period == null) {
            now = now.minusMonths(1);
            period = getPeriodByDate(fmt.format(now));
        }
        return period;
    }

    public String getMaxPeriodDate() {
        ScanResult scanResult = amazonDynamoDB.scan(new ScanRequest().withTableName("Period").withAttributesToGet("startDate"));
        List<Period> periods = dynamoDBMapper.marshallIntoObjects(Period.class, scanResult.getItems());
        return periods.stream()
                      .map(Period::getStartDate)
                      .max(Comparator.comparing(BY_DATE_ASC))
                      .orElse("2050-01");
    }

    public boolean periodNotExistsForDate(String date) {
        String[] dateParts = date.split("-");
        return dynamoDBMapper.load(Period.class, dateParts[0] + "-" + dateParts[1]) == null;
    }

    public void savePeriod(PeriodForm periodForm) {
        Period period = periodForm.toPeriod();

        List<String> categories = periodForm.getCategories();
        List<String> amounts = periodForm.getAmounts();
        Map<String, List<Category>> categoryMap = categoryService.getAllChildCategories(CategoryService.SORT_BY_PARENT_THEN_NAME).stream().collect(groupingBy(Category::getName));
        ImmutableList.Builder<Allocation> allocationsBuilder = new ImmutableList.Builder<>();

        for (int i = 0; i < categories.size(); i++) {
            double totalAllocated = Double.valueOf(amounts.get(i));

            if (totalAllocated < 0) {
                continue;
            }

            Allocation allocation = new Allocation();
            allocation.setCategory(categoryMap.get(categories.get(i)).get(0));
            allocation.setTotal(totalAllocated);
            allocationsBuilder.add(allocation);
        }

        period.setAllocations(allocationsBuilder.build());
        dynamoDBMapper.save(period);
    }

    private void sortAllocations(Period period) {
        List<Allocation> sortedAllocations = period.getAllocations().stream().sorted(BY_PARENT_CATEGORY.thenComparing(BY_CATEGORY_NAME)).collect(toImmutableList());
        period.setAllocations(sortedAllocations);
    }

    private void populateWithTransactions(Period period) {
        List<Transaction> transactions = period.getTransactions().stream().sorted(BY_NEWEST).collect(toImmutableList());
        period.setTransactions(transactions);
    }

    private void populateSpendByCategory(Period period) {
        Map<String, List<Transaction>> transactionMap = period.getTransactions().stream().collect(groupingBy(transaction -> transaction.getCategory().getName()));
        period.getAllocations().forEach(allocation -> {
            List<Transaction> categoryTransactions = transactionMap.getOrDefault(allocation.getCategory().getName(), ImmutableList.of());
            boolean isRainyDay = "Rainy Day".equals(allocation.getCategory().getName());
            allocation.setSpent(categoryTransactions
                    .stream()
                    .mapToDouble(t -> isRainyDay && t.getPrice() < 0 ? 0D : t.getPrice())
                    .sum());
            allocation.setCount(categoryTransactions.size());
        });
    }

    private List<Allocation> convertToAllocations(Map<Category, List<Transaction>> categoryTransactions,
                                                  Map<Category, List<Transaction>> subcategoryTransactions) {
        subcategoryTransactions.forEach((key, value) -> {
            if (!categoryTransactions.containsKey(key)) {
                categoryTransactions.put(key, ImmutableList.of());
            }
        });

        return categoryTransactions
                .entrySet()
                .stream()
                .map(transactions -> convertToAllocation(transactions, subcategoryTransactions))
                .collect(toImmutableList());
    }

    private Allocation convertToAllocation(Map.Entry<Category, List<Transaction>> entry,
                                           Map<Category, List<Transaction>> subcategoryTransactionMap) {
        Category category = entry.getKey();
        List<Transaction> transactions = entry.getValue();
        List<Transaction> subcategoryTransactions = subcategoryTransactionMap.containsKey(category)
                ? subcategoryTransactionMap.get(category) : ImmutableList.of();
        Allocation allocation = new Allocation();
        allocation.setCategory(category);
        allocation.setTotal(
                transactions.stream().mapToDouble(Transaction::getPrice).sum()
                        + subcategoryTransactions.stream().mapToDouble(t -> Math.abs(t.getPrice())).sum());
        allocation.setCount(transactions.size() + subcategoryTransactions.size());
        return allocation;
    }
}

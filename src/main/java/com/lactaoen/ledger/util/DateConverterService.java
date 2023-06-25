package com.lactaoen.ledger.util;

import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.Date;
import java.util.List;
import java.util.stream.IntStream;

import static com.google.common.collect.ImmutableList.toImmutableList;

@Component
public class DateConverterService {

    private static final SimpleDateFormat DYNAMO_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat DYNAMO_PERIOD_DATE_FORMAT = new SimpleDateFormat("yyyy-MM");
    private static final SimpleDateFormat MONTH_DAY_FORMAT = new SimpleDateFormat("MMM d");
    private static final SimpleDateFormat MONTH_YEAR_FORMAT = new SimpleDateFormat("MMM yyyy");
    private static final SimpleDateFormat YEAR_FORMAT = new SimpleDateFormat("yyyy");
    private static final SimpleDateFormat WEEK_OF_YEAR_FORMAT = new SimpleDateFormat("w");
    private static final SimpleDateFormat MONTH_FORMAT = new SimpleDateFormat("M");

    private static final int YEAR_START = 2018;

    public String convertToMonthDayFormat(String date) throws ParseException {
        return MONTH_DAY_FORMAT.format(DYNAMO_DATE_FORMAT.parse(date));
    }

    public String convertToMonthYearFormat(String date) throws ParseException {
        return MONTH_YEAR_FORMAT.format(DYNAMO_PERIOD_DATE_FORMAT.parse(date));
    }

    public String convertToYearFormat(String date) throws ParseException {
        return YEAR_FORMAT.format(DYNAMO_DATE_FORMAT.parse(date));
    }

    public int getWeekOfYearByDate(String date) {
        try {
            return Integer.parseInt(WEEK_OF_YEAR_FORMAT.format(DYNAMO_DATE_FORMAT.parse(date)));
        } catch (ParseException e) {
            return -1;
        }
    }

    public int getMonthByDate(String date) {
        try {
            return Integer.parseInt(MONTH_FORMAT.format(DYNAMO_DATE_FORMAT.parse(date)));
        } catch (ParseException e) {
            return -1;
        }
    }

    public String formatToDynamoDate(Date date) {
        return DYNAMO_DATE_FORMAT.format(date);
    }

    public Date stringToDate(String date) {
        try {
            return DYNAMO_DATE_FORMAT.parse(date);
        } catch (ParseException e) {
            return new Date();
        }
    }

    public List<Integer> getYearsSinceStart() {
        int currentYear = Year.now().getValue();
        return IntStream.range(YEAR_START, currentYear + 1).boxed().collect(toImmutableList());
    }
}

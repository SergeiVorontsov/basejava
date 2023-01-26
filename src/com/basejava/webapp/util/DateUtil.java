package com.basejava.webapp.util;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

import static java.time.temporal.ChronoField.DAY_OF_MONTH;

public class DateUtil {
    public static LocalDate of(int year, Month month) {
        return LocalDate.of(year, month, 1);
    }

    public static LocalDate of(String date) {
        if (date.length() == 7) {
            DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                    .appendPattern("MM/yyyy")
                    .parseDefaulting(DAY_OF_MONTH, 1)
                    .toFormatter();
            return LocalDate.parse(date, formatter);
        }
        if (date.length() == 10){
            DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                    .appendPattern("yyyy-MM-dd")
                    .parseDefaulting(DAY_OF_MONTH, 1)
                    .toFormatter();
            return LocalDate.parse(date, formatter);
        }
        return null;
    }

    public static LocalDate NOW() {
        return LocalDate.of(5000, 1, 1);
    }
}
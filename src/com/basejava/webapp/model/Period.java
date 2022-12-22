package com.basejava.webapp.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Period {
    private String title;
    private String description;
    private LocalDate dateFrom;
    private LocalDate dateTo;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(LocalDate dateFrom) {
        this.dateFrom = dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    public void setDateTo(LocalDate dateTo) {
        this.dateTo = dateTo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Period period = (Period) o;

        if (title != null ? !title.equals(period.title) : period.title != null) return false;
        if (description != null ? !description.equals(period.description) : period.description != null) return false;
        if (dateFrom != null ? !dateFrom.equals(period.dateFrom) : period.dateFrom != null) return false;
        return dateTo != null ? dateTo.equals(period.dateTo) : period.dateTo == null;
    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (dateFrom != null ? dateFrom.hashCode() : 0);
        result = 31 * result + (dateTo != null ? dateTo.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");

        return "\u001B[33m" +
                dateFrom.format(formatter) +
                "-" +
                dateTo.format(formatter) +
                "\u001B[0m" +
                " " +
                title +
                '\n' +
                description +
                '\n';
    }
}
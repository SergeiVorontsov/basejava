package com.basejava.webapp.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Period {
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;

    public Period(String title, String description, LocalDate startDate, LocalDate endDate) {
        Objects.requireNonNull(title, "title must not be null");
        Objects.requireNonNull(startDate, "startDate must not be null");
        Objects.requireNonNull(endDate, "endDate must not be null");
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
    }

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

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Period period = (Period) o;

        if (title != null ? !title.equals(period.title) : period.title != null) return false;
        if (description != null ? !description.equals(period.description) : period.description != null) return false;
        if (startDate != null ? !startDate.equals(period.startDate) : period.startDate != null) return false;
        return endDate != null ? endDate.equals(period.endDate) : period.endDate == null;
    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");

        return "\u001B[33m" +
                startDate.format(formatter) +
                "-" +
                endDate.format(formatter) +
                "\u001B[0m" +
                " " +
                title +
                '\n' +
                description +
                '\n';
    }
}
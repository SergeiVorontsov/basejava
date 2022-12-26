package com.basejava.webapp.model;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Company {
    private String title;
    private String website;
    private List<Period> periods;

    public Company(String title, String website, Period... periods) {
        Objects.requireNonNull(title, "title must not be null");
        Objects.requireNonNull(periods, "periods must not be null");
        this.title = title;
        this.website = website;
        this.periods = Arrays.asList(periods);
    }

    public Company(String title, String website, List<Period> periods) {
        Objects.requireNonNull(title, "title must not be null");
        Objects.requireNonNull(periods, "periods must not be null");
        this.title = title;
        this.website = website;
        this.periods = periods;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public List<Period> getPeriods() {
        return periods;
    }

    public void setPeriods(List<Period> periods) {
        this.periods = periods;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Company company = (Company) o;

        if (!title.equals(company.title)) return false;
        if (!Objects.equals(website, company.website)) return false;
        return periods.equals(company.periods);
    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (website != null ? website.hashCode() : 0);
        result = 31 * result + (periods != null ? periods.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(title).append(' ').append(website).append('\n');
        for (Period period : periods) {
                result.append(period).append('\n');
        }
        return result.toString();
    }
}
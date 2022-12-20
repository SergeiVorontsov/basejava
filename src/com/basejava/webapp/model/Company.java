package com.basejava.webapp.model;

import java.util.ArrayList;
import java.util.List;

public class Company {
    private String title;
    private String website;
    private List<Period> periods = new ArrayList<>();

    public Company() {

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

        if (title != null ? !title.equals(company.title) : company.title != null) return false;
        if (website != null ? !website.equals(company.website) : company.website != null) return false;
        return periods != null ? periods.equals(company.periods) : company.periods == null;
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
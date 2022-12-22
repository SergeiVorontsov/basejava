package com.basejava.webapp.model;

import java.util.ArrayList;
import java.util.List;

public class CompanySection extends AbstractSection {
    private final List<Company> companies = new ArrayList<>();

    public List<Company> getCompanies() {
        return companies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CompanySection that = (CompanySection) o;

        return companies.equals(that.companies);
    }

    @Override
    public int hashCode() {
        return companies.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (Company company : companies) {
            result.append(company);
        }
        return result.toString();
    }
}
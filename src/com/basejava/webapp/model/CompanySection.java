package com.basejava.webapp.model;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class CompanySection extends Section {
    private static final long serialVersionUID = 1L;

    private List<Company> companies;

    public CompanySection() {
    }

    public CompanySection(List<Company> companies) {
        Objects.requireNonNull(companies, "companies must not be null");
        this.companies = companies;
    }

    public CompanySection(Company... companies) {
        Objects.requireNonNull(companies, "companies must not be null");
        this.companies = Arrays.asList(companies);
    }

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
package com.basejava.webapp.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CompanySection extends AbstractSection {
    private final List<Company> companies = new ArrayList<>();

    @Override
    public Object getItem() {
        return companies;
    }

    @Override
    public void addItem(Object item) {
        if (item instanceof ArrayList) {
            ArrayList result = (ArrayList) item;
            this.companies.addAll(result);
        } else {
            throw new ClassCastException("The selected section cannot store this kind of data");
        }
    }

    @Override
    public void deleteItem() {
        Iterator<Company> iterator = companies.iterator();
        {
            while (iterator.hasNext()) {
                iterator.next();
                iterator.remove();
            }
        }
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
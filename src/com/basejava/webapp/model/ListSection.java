package com.basejava.webapp.model;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ListSection extends Section {
    private static final long serialVersionUID = 1L;

    private List<String> items;

    public ListSection() {
    }

    public ListSection(List<String> list) {
        Objects.requireNonNull(list, "items must not be null");
        this.items = list;
    }

    public ListSection(String... items) {
        Objects.requireNonNull(items, "strings must not be null");
        this.items = Arrays.asList(items);
    }

    public List<String> getItems() {
        return items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ListSection that = (ListSection) o;

        return items.equals(that.items);
    }

    @Override
    public int hashCode() {
        return items.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (String string : items) {
            result.append(string).append('\n');
        }
        return result.toString();
    }
}
package com.basejava.webapp.model;

import java.util.ArrayList;
import java.util.List;

public class ListSection extends AbstractSection {
    private final List<String> strings = new ArrayList<>();

    public List<String> getList() {
        return strings;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ListSection section = (ListSection) o;

        return strings.equals(section.strings);
    }

    @Override
    public int hashCode() {
        return strings.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (String string : strings) {
            result.append('\u2022').append(string).append('\n');
        }
        return result.toString();
    }
}

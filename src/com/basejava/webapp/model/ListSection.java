package com.basejava.webapp.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ListSection extends AbstractSection {
    private final List<String> strings = new ArrayList<>();

    @Override
    public Object getItem() {
        return strings;
    }

    @Override
    public void addItem(Object item) {
        if (item instanceof ArrayList) {
            ArrayList result = (ArrayList) item;
            this.strings.addAll(result);
        } else {
            throw new ClassCastException("The selected section cannot store this kind of data");
        }
    }

    @Override
    public void deleteItem() {
        Iterator<String> iterator = strings.iterator();
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

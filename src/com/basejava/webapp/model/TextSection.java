package com.basejava.webapp.model;

import java.util.Objects;

public class TextSection extends AbstractSection {
    private String text;

    @Override
    public Object getItem() {
        return text;
    }

    @Override
    public void addItem(Object item) {
        if (item instanceof String) {
            this.text = (String) item;
        } else {
            throw new ClassCastException("The selected section cannot store this kind of data");
        }
    }

    @Override
    public void deleteItem() {
            text = "";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TextSection that = (TextSection) o;

        return Objects.equals(text, that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(text);
    }

    @Override
    public String toString() {
        return text + '\n';
    }
}

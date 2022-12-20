package com.basejava.webapp.model;

import java.util.ArrayList;
import java.util.Objects;

public class Contact {

    private final ArrayList<String> value;

    public Contact() {
        value = new ArrayList<>();
    }

    public ArrayList<String> getValue() {
        return value;
    }

    public void addValue(String value) {
        Objects.requireNonNull(value, " value can`t be null");
        this.value.add(value);
    }

    public void deleteValue(String value) {
        Objects.requireNonNull(value, " value can`t be null");
        this.value.removeIf(s -> s.equals(value));
    }

    public void updateValue(String oldValue, String newValue) {
        Objects.requireNonNull(newValue, " value can`t be null");
        value.set(value.indexOf(oldValue), newValue);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Contact contact = (Contact) o;

        return Objects.equals(value, contact.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (String string : value) {
            result.append(string).append('\n');
        }
        return result.toString();
    }
}

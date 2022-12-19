package com.basejava.webapp.model;

import java.util.ArrayList;
import java.util.ListIterator;
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
        if (this.value.size() == 0) {
            this.value.add(value);
        } else {
            ListIterator<String> iter = this.value.listIterator();
            while (iter.hasNext()) {
                if (iter.next().equals(value)) {
                    throw new RuntimeException(value + " this contact already exist");
                } else {
                    iter.add(value);
                }
            }
        }
    }

    public void deleteValue(String value) {
        Objects.requireNonNull(value, " value can`t be null");
        ListIterator<String> iter = this.value.listIterator();
        while (iter.hasNext()) {
            if (iter.next().equals(value)) {
                iter.remove();
            } else {
                throw new RuntimeException(value + " this contact dont exist");
            }
        }
    }

    public void updateValue(String oldValue, String newValue) {
        Objects.requireNonNull(newValue, " value can`t be null");
        if (this.value.size() == 0) {
            throw new RuntimeException(oldValue + " this contact not exist");
        } else {
            ListIterator<String> iter = this.value.listIterator();
            while (iter.hasNext()) {
                if (iter.next().equals(oldValue)) {
                    iter.set(newValue);
                }
            }
        }
    }


    @Override
    public String toString() {
        return String.join(",", value);
    }
}

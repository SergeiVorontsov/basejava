package com.basejava.webapp.model;

import java.util.*;

public class ListSection extends Section<List<String>> {
    private List<String> value;
    {
        value = new ArrayList<>();
    }

    public List<String> getValue() {
        return value;
    }

    @Override
    public void addValue(List<String> value) {

    }

    @Override
    public void addValue(String value) {
            Objects.requireNonNull(value, " value can`t be null");
            if (this.value.size() == 0) {
                this.value.add((String)value);
            } else {
                ListIterator<String> iter = this.value.listIterator();
                while (iter.hasNext()) {
                    if (iter.next().equals(value)) {
                        throw new RuntimeException(value + " this contact already exist");
                    } else {
                        iter.add((String)value);
                    }
                }
            }
        }



  /*  public void setValue(ArrayList<String> value) {
        this.value = value;
    }*/
}

package com.basejava.webapp.model;

public class TextSection extends Section<String>{
    private String value;

    public String getValue() {
        return value;
    }

    @Override
    public void addValue( String value) {
        this.value = value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

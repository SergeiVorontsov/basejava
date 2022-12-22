package com.basejava.webapp.model;

import java.util.Objects;

public class TextSection extends AbstractSection {
    private String text;

    public TextSection() {
        this.text = "";
    }

    public String getText() {
        return text;
    }

    public void setText(String text){
        this.text = text;
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

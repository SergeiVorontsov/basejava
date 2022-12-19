package com.basejava.webapp.model;

public abstract class Section<T> {

    public abstract T getValue();

    public abstract void addValue(T value);

    public abstract void addValue(String value);
}

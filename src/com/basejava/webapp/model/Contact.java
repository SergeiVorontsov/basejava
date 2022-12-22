package com.basejava.webapp.model;

import java.util.Objects;

public class Contact {

    private String contact;

    public Contact() {
        this.contact = "";
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        Objects.requireNonNull(contact, " value can`t be null");
        this.contact = contact;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Contact contact1 = (Contact) o;

        return Objects.equals(contact, contact1.contact);
    }

    @Override
    public int hashCode() {
        return contact != null ? contact.hashCode() : 0;
    }

    @Override
    public String toString() {
        return contact + '\n';
    }
}

package com.basejava.webapp.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * Initial resume class
 */
public class Resume implements Comparable<Resume> {

    // Unique identifier
    private final String uuid;
    private String fullName;
    private Map<ContactType, Contact> contacts;
    private Map<SectionType, Section> sections;

    //Instance initialization block
    //All existing type of contacts and sections initialize in resume instantiation process
    {
        contacts = new HashMap<>();
        for (ContactType type : ContactType.values()) {
            contacts.put(type, new Contact());
        }

        sections = new HashMap<>();
        for (SectionType type : SectionType.values()) {
            if ((type == SectionType.QUALIFICATIONS) | (type == SectionType.ACHIEVEMENT)) {
                sections.put(type, new ListSection());
            }
            if ((type == SectionType.OBJECTIVE) | (type == SectionType.PERSONAL)) {
                sections.put(type, new TextSection());
            }
        }
    }

    public Resume() {
        this("");
    }

    public Resume(String fullName) {
        this(String.valueOf(UUID.randomUUID()), fullName);
    }

    public Resume(String uuid, String fullName) {
        Objects.requireNonNull(uuid, "uuid can`t be null");
        Objects.requireNonNull(fullName, "fullName can`t be null");
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return uuid;
    }

    public Map<SectionType, Section> getSections() {
        return sections;
    }

    public Map<ContactType, Contact> getContacts() {
        return contacts;
    }

    public void addContact(ContactType type, String value) {
        contacts.get(type).addValue(value);
    }

    public void deleteContact(ContactType type, String value) {
        contacts.get(type).deleteValue(value);
    }

    public void updateContact(ContactType type, String oldValue, String newValue) {
       contacts.get(type).updateValue(oldValue, newValue);
    }

    public void addSectionValue(SectionType type, Object value){ sections.get(type).addValue(value);

    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Resume resume = (Resume) o;

        if (!uuid.equals(resume.uuid)) return false;
        return fullName.equals(resume.fullName);
    }

    @Override
    public int hashCode() {
        int result = uuid.hashCode();
        result = 31 * result + fullName.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return uuid + '(' + fullName + ')';
    }

    @Override
    public int compareTo(Resume o) {
        return uuid.compareTo(o.uuid);
    }

}

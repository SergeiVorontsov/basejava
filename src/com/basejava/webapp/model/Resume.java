package com.basejava.webapp.model;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * Initial resume class
 */
public class Resume implements Comparable<Resume> {

    private final String uuid;
    private final String fullName;
    private final Map<ContactType, Contact> contacts = new EnumMap<>(ContactType.class);
    private final Map<SectionType, AbstractSection> sections = new EnumMap<>(SectionType.class);

    public Resume() {
        this("");
    }

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        Objects.requireNonNull(uuid, "uuid can`t be null");
        Objects.requireNonNull(fullName, "fullName can`t be null");
        this.uuid = uuid;
        this.fullName = fullName;

        for (ContactType type : ContactType.values()) {
            contacts.put(type, new Contact());
        }

        for (SectionType type : SectionType.values()) {
            if ((type == SectionType.QUALIFICATIONS) | (type == SectionType.ACHIEVEMENT)) {
                sections.put(type, new ListSection());
            }
            if ((type == SectionType.OBJECTIVE) | (type == SectionType.PERSONAL)) {
                sections.put(type, new TextSection());
            }
            if ((type == SectionType.EXPERIENCE) | (type == SectionType.EDUCATION)) {
                sections.put(type, new CompanySection());
            }
        }
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    // Contacts
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

    // Sections
    public Map<SectionType, AbstractSection> getSections() {
        return sections;
    }

    public void addSectionValue(SectionType type, Object item) {
        sections.get(type).addItem(item);
    }

    public void deleteSectionValue(SectionType type) {
        sections.get(type).deleteItem();
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
        StringBuilder contactsResult = new StringBuilder();
        for (Map.Entry<ContactType, Contact> entry : getContacts().entrySet()) {
            String key = entry.getKey().getTitle();
            String value = entry.getValue().toString();
            contactsResult.append("\u001B[32m").append(key).append(": ").append("\u001B[0m").append(value);
        }
        StringBuilder sectionResult = new StringBuilder();
        for (Map.Entry<SectionType, AbstractSection> entry : getSections().entrySet()) {
            String key = entry.getKey().getTitle();
            String value = entry.getValue().toString();
            sectionResult.append("\u001B[32m").append(key).append(": ").append("\u001B[0m").append('\n').append(value);
        }

        return uuid + '\n'
                + '(' + fullName + ')' + '\n'
                + '\n'
                + contactsResult
                + '\n'
                + sectionResult;
    }

    @Override
    public int compareTo(Resume o) {
        return uuid.compareTo(o.uuid);
    }
}
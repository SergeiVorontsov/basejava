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
    private String fullName;
    private final Map<ContactType, String> contacts = new EnumMap<>(ContactType.class);
    private final Map<SectionType, Section> sections = new EnumMap<>(SectionType.class);

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
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
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Map<ContactType, String> getContacts() {
        return contacts;
        //Collections.unmodifiableMap(contacts);
    }

    public Map<SectionType, Section> getSections() {
        return sections;
        //Collections.unmodifiableMap(sections);
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
        for (Map.Entry<ContactType, String> entry : getContacts().entrySet()) {
            String key = entry.getKey().getTitle();
            String value = entry.getValue();
            contactsResult.append("\u001B[32m").append(key).append(": ").append("\u001B[0m").append(value).append('\n');
        }
        StringBuilder sectionResult = new StringBuilder();
        for (Map.Entry<SectionType, Section> entry : getSections().entrySet()) {
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
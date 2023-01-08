package com.basejava.webapp.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * Initial resume class
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Resume implements Comparable<Resume>, Serializable {
    private static final long serialVersionUID = 1L;

    private String uuid;
    private String fullName;
    private final Map<ContactType, String> contacts = new EnumMap<>(ContactType.class);
    private final Map<SectionType, Section> sections = new EnumMap<>(SectionType.class);

    public Resume() {
    }

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
    }

    public void setContact(ContactType type, String value) {
        contacts.put(type, value);
    }

    public Map<SectionType, Section> getSections() {
        return sections;
    }

    public void setSection(SectionType type, Section section) {
        sections.put(type, section);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return Objects.equals(uuid, resume.uuid) && Objects.equals(fullName, resume.fullName) && Objects.equals(contacts, resume.contacts) && Objects.equals(sections, resume.sections);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, fullName, contacts, sections);
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
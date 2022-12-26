package com.basejava.webapp.util;

import com.basejava.webapp.model.*;

import java.util.ArrayList;
import java.util.List;

public class ResumeUtil {
    public static void addContact(Resume resume, ContactType type, String value) {
        resume.getContacts().put(type, value);
    }

    public static void removeContact(Resume resume, ContactType type) {
        resume.getContacts().remove(type);
    }

    public static void addCompany(Resume resume, Company company, SectionType type) {
        if ((type == SectionType.EXPERIENCE) | (type == SectionType.EDUCATION)) {
            CompanySection temp = (CompanySection) resume.getSections().get(type);
            if (temp == null) {
                ArrayList<Company> companies = new ArrayList<>();
                companies.add(company);
                CompanySection companySection = new CompanySection(companies);
                resume.getSections().put(type, companySection);
            } else if (!temp.getCompanies().contains(company)) {
                temp.getCompanies().add(company);
            } else {
                temp.getCompanies().set((temp.getCompanies().indexOf(company)), company);
            }
        } else {
            throw new IllegalArgumentException("type must be SectionType.EXPERIENCE or SectionType.EDUCATION");
        }
    }

    public static void addList(Resume resume, SectionType type, List<String> items) {
        if ((type == SectionType.ACHIEVEMENT) | (type == SectionType.QUALIFICATIONS)) {
            ListSection temp = (ListSection) resume.getSections().get(type);
            if (temp == null) {
                ListSection listSection = new ListSection(items);
                resume.getSections().put(type, listSection);
            } else if (temp.getItems() == null) {
                temp.getItems().addAll(items);
            } else {
                temp.getItems().clear();
                temp.getItems().addAll(items);
            }
        } else {
            throw new IllegalArgumentException("type must be SectionType.ACHIEVEMENT or SectionType.QUALIFICATIONS");
        }
    }

    public static void addText(Resume resume, SectionType type, String text) {
        if ((type == SectionType.OBJECTIVE) | (type == SectionType.PERSONAL)) {
            TextSection temp = (TextSection) resume.getSections().get(type);
            if (temp == null) {
                TextSection textSection = new TextSection(text);
                resume.getSections().put(type, textSection);
            } else if (temp.getContent() == null) {
                removeSection(resume, type);
                TextSection textSection = new TextSection(text);
                resume.getSections().put(type, textSection);
            }
        } else {
            throw new IllegalArgumentException("type must be SectionType.OBJECTIVE or SectionType.PERSONAL");
        }
    }

    public static void removeSection(Resume resume, SectionType type) {
        resume.getSections().remove(type);
    }
}
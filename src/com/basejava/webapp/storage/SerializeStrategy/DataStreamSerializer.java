package com.basejava.webapp.storage.SerializeStrategy;

import com.basejava.webapp.model.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements Serializer {
    @Override
    public void doWrite(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            writeContacts(resume, dos);
            writeSections(resume, dos);
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            readContacts(dis, resume);
            readSections(dis, resume);
            return resume;
        }
    }

    private void writeContacts(Resume resume, DataOutputStream dos) throws IOException {
        Map<ContactType, String> contacts = resume.getContacts();
        dos.writeInt(contacts.size());
        for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
            dos.writeUTF(entry.getKey().name());
            dos.writeUTF(entry.getValue());
        }
    }

    private void writeSections(Resume resume, DataOutputStream dos) throws IOException {
        Map<SectionType, Section> sections = resume.getSections();
        dos.writeInt(sections.size());
        for (Map.Entry<SectionType, Section> entry : sections.entrySet()) {
            SectionType sectionType = entry.getKey();
            dos.writeUTF(sectionType.name());
            switch (sectionType) {
                case OBJECTIVE:
                case PERSONAL:
                    TextSection objective = (TextSection) entry.getValue();
                    dos.writeUTF(objective.getContent());
                    break;
                case ACHIEVEMENT:
                case QUALIFICATIONS:
                    ListSection achievement = (ListSection) entry.getValue();
                    dos.writeInt(achievement.getItems().size());
                    for (String item : achievement.getItems()) {
                        dos.writeUTF(item);
                    }
                    break;
                case EXPERIENCE:
                case EDUCATION:
                    CompanySection experience = (CompanySection) entry.getValue();
                    dos.writeInt(experience.getCompanies().size());
                    for (Company company : experience.getCompanies()) {
                        dos.writeUTF(company.getTitle());
                        dos.writeUTF(company.getWebsite());
                        dos.writeInt(company.getPeriods().size());
                        for (Company.Period period : company.getPeriods()) {
                            dos.writeUTF(period.getTitle());
                            dos.writeUTF(period.getDescription());
                            dos.writeUTF(period.getStartDate().toString());
                            dos.writeUTF(period.getEndDate().toString());
                        }
                    }
                    break;
            }
        }
    }

    private void readContacts(DataInputStream dis, Resume resume) throws IOException {
        int sizeContacts = dis.readInt();
        for (int i = 0; i < sizeContacts; i++) {
            resume.setContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
        }
    }

    private void readSections(DataInputStream dis, Resume resume) throws IOException {
        int sizeSections = dis.readInt();
        for (int i = 0; i < sizeSections; i++) {
            String sectionType = dis.readUTF();
            switch (sectionType) {
                case "OBJECTIVE":
                    resume.setSection(SectionType.OBJECTIVE, new TextSection(dis.readUTF()));
                    break;
                case "PERSONAL":
                    resume.setSection(SectionType.PERSONAL, new TextSection(dis.readUTF()));
                    break;
                case "ACHIEVEMENT":
                    resume.setSection(SectionType.ACHIEVEMENT, new ListSection(getStrings(dis)));
                    break;
                case "QUALIFICATIONS":
                    resume.setSection(SectionType.QUALIFICATIONS, new ListSection(getStrings(dis)));
                    break;
                case "EXPERIENCE":
                    resume.setSection(SectionType.EXPERIENCE, new CompanySection(getCompanies(dis)));
                    break;
                case "EDUCATION":
                    resume.setSection(SectionType.EDUCATION, new CompanySection(getCompanies(dis)));
                    break;
            }
        }
    }

    private List<String> getStrings(DataInputStream dis) throws IOException {
        int sizeAchievement = dis.readInt();
        List<String> items = new ArrayList<>();
        for (int k = 0; k < sizeAchievement; k++) {
            items.add(dis.readUTF());
        }
        return items;
    }

    private List<Company> getCompanies(DataInputStream dis) throws IOException {
        int sizeCompanies = dis.readInt();
        List<Company> items = new ArrayList<>();
        for (int k = 0; k < sizeCompanies; k++) {
            Company company = new Company(dis.readUTF(), dis.readUTF());
            int sizePeriods = dis.readInt();
            List<Company.Period> periods = new ArrayList<>();
            for (int j = 0; j < sizePeriods; j++) {
                periods.add(new Company.Period(dis.readUTF(), dis.readUTF(), dis.readUTF(), dis.readUTF()));
            }
            company.setPeriods(periods);
            items.add(company);
        }
        return items;
    }
}
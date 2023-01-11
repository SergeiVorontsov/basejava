package com.basejava.webapp.storage.SerializeStrategy;

import com.basejava.webapp.model.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

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
            readEachWithException(resume, dis, r -> r.setContact(ContactType.valueOf(dis.readUTF()), dis.readUTF()));
            readEachWithException(resume, dis, r -> {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                switch (sectionType) {
                    case OBJECTIVE:
                    case PERSONAL:
                        resume.setSection(sectionType, new TextSection(dis.readUTF()));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        List<String> strings = new ArrayList<>();
                        readEachWithException(strings, dis, s -> s.add(dis.readUTF()));
                        resume.setSection(sectionType, new ListSection(strings));
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        List<Company> companies = new ArrayList<>();
                        readEachWithException(companies, dis, c -> {
                            Company company = new Company(dis.readUTF(), checkNullParam(dis));
                            List<Company.Period> periods = new ArrayList<>();
                            readEachWithException(periods, dis, p -> p.add(new Company.Period(dis.readUTF(), checkNullParam(dis), dis.readUTF(), dis.readUTF())));
                            company.setPeriods(periods);
                            c.add(company);
                        });
                        r.setSection(sectionType, new CompanySection(companies));
                        break;
                }
            });
            return resume;
        }
    }

    private void writeContacts(Resume resume, DataOutputStream dos) throws IOException {
        writeEachWithException(resume.getContacts().entrySet(), dos, contactTypeStringEntry -> {
            dos.writeUTF(contactTypeStringEntry.getKey().name());
            dos.writeUTF(contactTypeStringEntry.getValue());
        });
    }

    private void writeSections(Resume resume, DataOutputStream dos) throws IOException {
        writeEachWithException(resume.getSections().entrySet(), dos, sectionTypeSectionEntry -> {
            SectionType sectionType = sectionTypeSectionEntry.getKey();
            dos.writeUTF(sectionType.name());
            switch (sectionType) {
                case OBJECTIVE:
                case PERSONAL:
                    TextSection objective = (TextSection) sectionTypeSectionEntry.getValue();
                    dos.writeUTF(objective.getContent());
                    break;
                case ACHIEVEMENT:
                case QUALIFICATIONS:
                    ListSection achievement = (ListSection) sectionTypeSectionEntry.getValue();
                    writeEachWithException(achievement.getItems(), dos, dos::writeUTF);
                    break;
                case EXPERIENCE:
                case EDUCATION:
                    CompanySection experience = (CompanySection) sectionTypeSectionEntry.getValue();
                    writeEachWithException(experience.getCompanies(), dos, company -> {
                        dos.writeUTF(company.getTitle());
                        dos.writeUTF(checkNullParam(company.getWebsite()));
                        writeEachWithException(company.getPeriods(), dos, period -> {
                            dos.writeUTF(period.getTitle());
                            dos.writeUTF(DataStreamSerializer.this.checkNullParam(period.getDescription()));
                            dos.writeUTF(period.getStartDate().toString());
                            dos.writeUTF(period.getEndDate().toString());
                        });
                    });
                    break;
            }
        });
    }

    private <K> void writeEachWithException(Collection<K> collection, DataOutputStream dos, CustomConsumer<K> action) throws IOException {
        Objects.requireNonNull(action);
        dos.writeInt(collection.size());
        for (K k : collection) {
            action.accept(k);
        }
    }

    private <K> void readEachWithException(K k, DataInputStream dis, CustomConsumer<K> action) throws IOException {
        Objects.requireNonNull(action);
        int counter = dis.readInt();
        for (int i = 0; i < counter; i++) {
            action.accept(k);

        }
    }

    private String checkNullParam(DataInputStream dis) throws IOException {
        String param = dis.readUTF();
        if (param.equals("null")) {
            return null;
        }
        return param;
    }

    private String checkNullParam(String param) {
        if (param == null) {
            return "null";
        }
        return param;
    }
}
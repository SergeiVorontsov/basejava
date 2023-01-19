package com.basejava.webapp.storage;

import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.*;
import com.basejava.webapp.sql.SqlHelper;

import java.sql.*;
import java.util.*;
import java.util.logging.Logger;

public class SqlStorage implements Storage {
    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());

    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword, String driver) {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public int size() {
        return sqlHelper.execute("SELECT count(*) FROM resume", statement -> {
            ResultSet rs = statement.executeQuery();
            return !rs.next() ? 0 : rs.getInt(1);
        });
    }

    @Override
    public void clear() {
        sqlHelper.execute("DELETE FROM resume");
    }

    @Override
    public List<Resume> getAllSorted() {
        LOG.info("getAllSorted");
        return sqlHelper.transactionExecute(conn -> {
            Map<String, Resume> resumes = new LinkedHashMap<>();
            try (PreparedStatement statement = conn.prepareStatement("" +
                    "SELECT * FROM resume r " +
                    "ORDER BY full_name ")) {
                ResultSet rs = statement.executeQuery();
                while (rs.next()) {
                    resumes.put(rs.getString("uuid"),
                            new Resume(rs.getString("uuid"), rs.getString("full_name")));
                }
            }
            try (PreparedStatement statement = conn.prepareStatement("" +
                    "SELECT resume_uuid,contact_type,contact_value " +
                    "FROM contact c " +
                    "ORDER BY contact_type ")) {
                ResultSet rs = statement.executeQuery();
                while (rs.next()) {
                    getContact(rs, resumes.get(rs.getString("resume_uuid")));
                }
            }
            try (PreparedStatement statement = conn.prepareStatement("" +
                    "SELECT resume_uuid,section_type,section_value " +
                    "FROM section c " +
                    "ORDER BY section_type")) {
                ResultSet rs = statement.executeQuery();
                while (rs.next()) {
                    getSection(rs, resumes.get(rs.getString("resume_uuid")));
                }
            }
            return new ArrayList<>(resumes.values());
        });
    }

    @Override
    public void update(Resume resume) throws StorageException {
        LOG.info("Update " + resume);
        sqlHelper.transactionExecute(conn -> {
            try (PreparedStatement statement = conn.prepareStatement("UPDATE resume SET full_name=? WHERE uuid=?")) {
                statement.setString(1, resume.getFullName());
                statement.setString(2, resume.getUuid());
                if (statement.executeUpdate() == 0) {
                    throw new NotExistStorageException(resume.getUuid());
                }
            }
            removeContact(resume, conn);
            setContact(resume, conn);
            removeSection(resume, conn);
            setSection(resume, conn);
            return null;
        });
    }

    @Override
    public void save(Resume resume) throws StorageException {
        LOG.info("Save " + resume);
        sqlHelper.transactionExecute(conn -> {
                    try (PreparedStatement statement = conn.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)")) {
                        statement.setString(1, resume.getUuid());
                        statement.setString(2, resume.getFullName());
                        statement.execute();
                    }
                    setContact(resume, conn);
                    setSection(resume, conn);
                    return null;
                }
        );
    }

    @Override
    public void delete(String uuid) throws NotExistStorageException {
        LOG.info("Delete " + uuid);
        sqlHelper.execute("DELETE FROM resume WHERE uuid=?", preparedStatement -> {
            preparedStatement.setString(1, uuid);
            if (preparedStatement.executeUpdate() == 0)
                throw new NotExistStorageException(uuid);
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        LOG.info("Get " + uuid);
        return sqlHelper.transactionExecute(conn -> {
            Resume resume;
            try (PreparedStatement statement = conn.prepareStatement("SELECT * FROM resume WHERE uuid=?\n")) {
                statement.setString(1, uuid);
                ResultSet rs = statement.executeQuery();
                if (rs.next()) {
                    resume = new Resume(rs.getString("uuid"), rs.getString("full_name"));
                } else {
                    throw new NotExistStorageException(uuid);
                }
            }
            try (PreparedStatement statement = conn.prepareStatement("SELECT * FROM contact WHERE resume_uuid=?\n")) {
                statement.setString(1, uuid);
                ResultSet rs = statement.executeQuery();
                while (rs.next()) {
                    getContact(rs, resume);
                }
            }
            try (PreparedStatement statement = conn.prepareStatement("SELECT * FROM section WHERE resume_uuid=?\n")) {
                statement.setString(1, uuid);
                ResultSet rs = statement.executeQuery();
                while (rs.next()) {
                    getSection(rs, resume);
                }
            }
            return resume;
        });
    }

    private void getContact(ResultSet rs, Resume resume) throws SQLException {
        {
            String value = rs.getString("contact_value");
            if (value != null) {
                ContactType type = ContactType.valueOf(rs.getString("contact_type"));
                resume.getContacts().put(type, value);
            }
        }
    }

    private void getSection(ResultSet rs, Resume resume) throws SQLException {
        {
            SectionType type = SectionType.valueOf(rs.getString("section_type"));
            String value = rs.getString("section_value");
            if (value != null) {
                switch (type) {
                    case OBJECTIVE:
                    case PERSONAL:
                        resume.setSection(type, new TextSection(value));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        List<String> list = new ArrayList<>(Arrays.asList(value.split("\n")));
                        resume.setSection(type, new ListSection(list));
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        break;
                }
            }
        }
    }

    private void setContact(Resume resume, Connection conn) throws SQLException {
        try (PreparedStatement statement = conn.prepareStatement("INSERT INTO contact (resume_uuid, contact_type, contact_value) VALUES (?,?,?)")) {
            for (Map.Entry<ContactType, String> entry : resume.getContacts().entrySet()) {
                statement.setString(1, resume.getUuid());
                statement.setString(2, entry.getKey().name());
                statement.setString(3, entry.getValue());
                statement.addBatch();
            }
            statement.executeBatch();
        }
    }

    private void setSection(Resume resume, Connection conn) throws SQLException {
        try (PreparedStatement statement = conn.prepareStatement("INSERT INTO section (resume_uuid, section_type, section_value) VALUES (?,?,?)")) {
            for (Map.Entry<SectionType, Section> entry : resume.getSections().entrySet()) {
                statement.setString(1, resume.getUuid());
                statement.setString(2, entry.getKey().name());
                Section section = entry.getValue();
                switch (SectionType.valueOf(entry.getKey().name())) {
                    case OBJECTIVE:
                    case PERSONAL:
                        TextSection ts = (TextSection) section;
                        statement.setString(3, ts.getContent());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        ListSection ls = (ListSection) section;
                        statement.setString(3, String.join("\n", ls.getItems()));
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        break;
                }
                statement.addBatch();
            }
            statement.executeBatch();
        }
    }

    private void removeContact(Resume resume, Connection conn) throws SQLException {
        try (PreparedStatement statement = conn.prepareStatement("DELETE FROM contact WHERE resume_uuid=?")) {
            statement.setString(1, resume.getUuid());
            if (statement.executeUpdate() == 0) {
                throw new StorageException("Contacts have not been updated", resume.getUuid());
            }
        }
    }

    private void removeSection(Resume resume, Connection conn) throws SQLException {
        try (PreparedStatement statement = conn.prepareStatement("DELETE FROM section WHERE resume_uuid=?")) {
            statement.setString(1, resume.getUuid());
            if (statement.executeUpdate() == 0) {
                throw new StorageException("Section have not been updated", resume.getUuid());
            }
        }
    }
}
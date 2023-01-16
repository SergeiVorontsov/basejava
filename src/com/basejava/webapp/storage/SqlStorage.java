package com.basejava.webapp.storage;

import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.ContactType;
import com.basejava.webapp.model.Resume;
import com.basejava.webapp.sql.SqlHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class SqlStorage implements Storage {
    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());

    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
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
        return sqlHelper.execute("" +
                        "SELECT * FROM resume r " +
                        "LEFT JOIN contact c " +
                        "ON r.uuid = c.resume_uuid " +
                        "ORDER BY full_name ",
                statement -> {
                    ResultSet rs = statement.executeQuery();
                    List<Resume> resumes = new ArrayList<>();
                    while (rs.next()) {
                        Resume resume = new Resume(rs.getString("uuid"), rs.getString("full_name"));
                        getContact(rs, resume);
                        resumes.add(resume);
                    }
                    return resumes;
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
        return sqlHelper.execute("" +
                        "SELECT * FROM resume r " +
                        "LEFT JOIN contact c " +
                        "ON r.uuid = c.resume_uuid " +
                        "WHERE r.uuid =? ",
                statement -> {
                    statement.setString(1, uuid);
                    ResultSet rs = statement.executeQuery();
                    if (!rs.next()) {
                        throw new NotExistStorageException(uuid);
                    }
                    Resume resume = new Resume(uuid, rs.getString("full_name"));
                    getContact(rs, resume);
                    return resume;
                });
    }

    private void getContact(ResultSet rs, Resume resume) throws SQLException {
        if (rs.getString("value") != null) {
            do {
                String value = rs.getString("value");
                ContactType type = ContactType.valueOf(rs.getString("type"));
                resume.getContacts().put(type, value);
            } while (rs.next() && resume.getUuid().equals(rs.getString("uuid")));
            rs.previous();
        }
    }

    private void setContact(Resume resume, Connection conn) throws SQLException {
        try (PreparedStatement statement = conn.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)")) {
            for (Map.Entry<ContactType, String> entry : resume.getContacts().entrySet()) {
                statement.setString(1, resume.getUuid());
                statement.setString(2, entry.getKey().name());
                statement.setString(3, entry.getValue());
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
}
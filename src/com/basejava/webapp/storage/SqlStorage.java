package com.basejava.webapp.storage;

import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;
import com.basejava.webapp.sql.SqlHelper;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class SqlStorage implements Storage {
    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());

    private final SqlHelper sqlHelper;

    public SqlStorage(SqlHelper sqlHelper) {
        this.sqlHelper = sqlHelper;
    }

    @Override
    public int size() {
        String sqlRequest = "SELECT count(*) FROM resume";
        return sqlHelper.execute(sqlRequest, statement -> {
            ResultSet rs = statement.executeQuery();
            return !rs.next() ? 0 : rs.getInt(1);
        });
    }

    @Override
    public void clear() {
        String sqlRequest = "DELETE FROM resume";
        sqlHelper.execute(sqlRequest);
    }

    @Override
    public List<Resume> getAllSorted() {
        LOG.info("getAllSorted");
        String sqlRequest = "SELECT * FROM resume ORDER BY full_name";
        return sqlHelper.execute(sqlRequest, statement -> {
            ResultSet rs = statement.executeQuery();
            List<Resume> resumes = new ArrayList<>();
            while (rs.next()) {
                resumes.add(new Resume(rs.getString("uuid"), rs.getString("full_name")));
            }
            return resumes;
        });
    }

    @Override
    public void update(Resume resume) throws StorageException {
        LOG.info("Update " + resume);
        String sqlRequest = "UPDATE resume SET full_name=? WHERE uuid=?";
        sqlHelper.execute(sqlRequest, statement -> {
            statement.setString(1, resume.getFullName());
            statement.setString(2, resume.getUuid());
            if (statement.executeUpdate() == 0) {
                throw new NotExistStorageException(resume.getUuid());
            }
            return null;
        });
    }

    @Override
    public void save(Resume resume) throws StorageException {
        LOG.info("Save " + resume);
        String sqlRequest = "INSERT INTO resume (uuid, full_name) VALUES (?,?)";
        sqlHelper.execute(sqlRequest, statement -> {
            statement.setString(1, resume.getUuid());
            statement.setString(2, resume.getFullName());
            statement.execute();
            return null;
        });
    }

    @Override
    public void delete(String uuid) throws NotExistStorageException {
        LOG.info("Delete " + uuid);
        String sqlRequest = "DELETE FROM resume WHERE uuid=?";
        sqlHelper.execute(sqlRequest, preparedStatement -> {
            preparedStatement.setString(1, uuid);
            if (preparedStatement.executeUpdate() == 0)
                throw new NotExistStorageException(uuid);
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        LOG.info("Get " + uuid);
        String sqlRequest = "SELECT * FROM resume r WHERE r.uuid =?";
        return sqlHelper.execute(sqlRequest, statement -> {
            statement.setString(1, uuid);
            ResultSet rs = statement.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            return new Resume(uuid, rs.getString("full_name"));
        });
    }
}

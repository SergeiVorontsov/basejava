package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public abstract int size();

    @Override
    public abstract void clear();

    @Override
    public abstract Resume[] getAll();

    @Override
    public void update(Resume resume) throws NotExistStorageException {
        int index = getIndex(resume.getUuid());
        if (index >= 0) {
            setResume(resume, index);
        } else {
            throw new NotExistStorageException(resume.getUuid());
        }

    }

    @Override
    public void save(Resume resume) throws StorageException {
        int index = getIndex(resume.getUuid());
        if (index >= 0) {
            throw new ExistStorageException(resume.getUuid());
        }
        insertResume(resume, index);
    }

    @Override
    public void delete(String uuid) throws NotExistStorageException {
        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        } else {
            removeResume(index);
        }
    }

    @Override
    public Resume get(String uuid) throws NotExistStorageException {
        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        return getResume(index);
    }

    protected abstract void insertResume(Resume resume, int index) throws StorageException;

    protected abstract void removeResume(int index);

    protected abstract void setResume(Resume resume, int index);

    protected abstract int getIndex(String uuid);

    protected abstract Resume getResume(int index);
}
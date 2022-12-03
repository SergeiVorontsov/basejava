package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public void update(Resume resume) throws NotExistStorageException {
        setResume(resume, getExistingSearchKey(resume.getUuid()));
    }

    @Override
    public void save(Resume resume) throws StorageException {
        insertResume(resume, getNotExistingSearchKey(resume.getUuid()));
    }

    @Override
    public void delete(String uuid) throws NotExistStorageException {
        removeResume(getExistingSearchKey(uuid));
    }

    @Override
    public Resume get(String uuid) throws NotExistStorageException {
        return getResume(getExistingSearchKey(uuid));
    }

    private Object getExistingSearchKey(String uuid) throws NotExistStorageException {
        Object searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    private Object getNotExistingSearchKey(String uuid) throws ExistStorageException {
        Object searchKey = getSearchKey(uuid);
        if (isExist(searchKey)) {
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }

    public abstract int size();

    public abstract void clear();

    public abstract Resume[] getAll();

    protected abstract void insertResume(Resume resume, Object searchKey) throws StorageException;

    protected abstract void removeResume(Object searchKey);

    protected abstract void setResume(Resume resume, Object searchKey);

    protected abstract Resume getResume(Object searchKey);

    protected abstract Object getSearchKey(String uuid);

    protected abstract boolean isExist(Object searchKey);
}
package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public void update(Resume resume) throws NotExistStorageException {
        Object searchKey = getSearchKey(resume.getUuid());
        getExistingSearchKey(searchKey, resume.getUuid());
        setResume(resume, searchKey);
    }

    @Override
    public void save(Resume resume) throws StorageException {
        Object searchKey = getSearchKey(resume.getUuid());
        getNotExistingSearchKey(searchKey, resume.getUuid());
        insertResume(resume, searchKey);
    }

    @Override
    public void delete(String uuid) throws NotExistStorageException {
        Object searchKey = getSearchKey(uuid);
        getExistingSearchKey(searchKey, uuid);
        removeResume(searchKey);
    }

    @Override
    public Resume get(String uuid) throws NotExistStorageException {
        Object searchKey = getSearchKey(uuid);
        getExistingSearchKey(searchKey, uuid);
        return getResume(searchKey);
    }

    private void getExistingSearchKey(Object searchKey, String uuid) throws NotExistStorageException {
        if (!isExist(searchKey))
            throw new NotExistStorageException(uuid);
    }

    private void getNotExistingSearchKey(Object searchKey, String uuid) throws ExistStorageException {
        if (isExist(searchKey))
            throw new ExistStorageException(uuid);
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
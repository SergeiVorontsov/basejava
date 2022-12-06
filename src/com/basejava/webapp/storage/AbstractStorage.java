package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;

import java.util.Comparator;
import java.util.List;

public abstract class AbstractStorage implements Storage {

    private static final Comparator<Resume> resumeComparator
            = Comparator.nullsLast(Comparator.comparing(Resume::getFullName, String::compareTo)
            .thenComparing(Resume::getUuid, String::compareTo));

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

    public List<Resume> getAllSorted() {
        List<Resume> list = getListStorage();
        list.sort(resumeComparator);
        return list;
    }

    public abstract int size();

    public abstract void clear();

    protected abstract List<Resume> getListStorage();

    protected abstract void insertResume(Resume resume, Object searchKey) throws StorageException;

    protected abstract void removeResume(Object searchKey);

    protected abstract void setResume(Resume resume, Object searchKey);

    protected abstract Resume getResume(Object searchKey);

    protected abstract Object getSearchKey(String uuid);

    protected abstract boolean isExist(Object searchKey);
}
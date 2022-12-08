package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;

import java.util.Comparator;
import java.util.List;

public abstract class AbstractStorage<T> implements Storage {

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

    private T getExistingSearchKey(String uuid) throws NotExistStorageException {
        T searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    private T getNotExistingSearchKey(String uuid) throws ExistStorageException {
        T searchKey = getSearchKey(uuid);
        if (isExist(searchKey)) {
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }

    public List<Resume> getAllSorted() {
        List<Resume> list = getCopyStorageList();
        list.sort(resumeComparator);
        return list;
    }

    public abstract int size();

    public abstract void clear();

    protected abstract List<Resume> getCopyStorageList();

    protected abstract void insertResume(Resume resume, T searchKey) throws StorageException;

    protected abstract void removeResume(T searchKey);

    protected abstract void setResume(Resume resume, T searchKey);

    protected abstract Resume getResume(T searchKey);

    protected abstract T getSearchKey(String uuid);

    protected abstract boolean isExist(T searchKey);
}
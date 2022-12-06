package com.basejava.webapp.storage;

import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;

import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.asList;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10000;

    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int countResumes = 0;

    @Override
    public int size() {
        return countResumes;
    }

    @Override
    public void clear() {
        Arrays.fill(storage, 0, countResumes, null);
        countResumes = 0;
    }

    @Override
    public List<Resume> getListStorage() {
        return asList(Arrays.copyOf(storage, countResumes));
    }


    @Override
    protected void setResume(Resume resume, Object searchKey) {
        storage[(int) searchKey] = resume;
    }

    @Override
    protected void insertResume(Resume resume, Object searchKey) throws StorageException {
        if (STORAGE_LIMIT == countResumes) {
            throw new StorageException("Resume database is full", resume.getUuid());
        } else {
            insertResumeInArray(resume, (int) searchKey);
            countResumes++;
        }
    }

    @Override
    protected void removeResume(Object searchKey) {
        removeResumeInArray((int) searchKey);
        storage[countResumes - 1] = null;
        countResumes--;
    }

    @Override
    protected Resume getResume(Object searchKey) {
        return storage[(int) searchKey];
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return (int) searchKey >= 0;
    }

    protected abstract void insertResumeInArray(Resume resume, int index);

    protected abstract void removeResumeInArray(int index);
}

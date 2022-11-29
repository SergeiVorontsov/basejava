package com.basejava.webapp.storage;

import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;

import java.util.Arrays;

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
    public Resume[] getAll() {
        return Arrays.copyOf(storage, countResumes);
    }

    @Override
    protected void setResume(Resume resume, int index) {
        storage[index] = resume;
    }

    @Override
    protected void insertResume(Resume resume, int index) throws StorageException {
        if (STORAGE_LIMIT == countResumes) {
            throw new StorageException("Resume database is full", resume.getUuid());
        } else {
            insertResumeInArray(resume, index);
            countResumes++;
        }
    }

    @Override
    protected void removeResume(int index) {
        removeResumeInArray(index);
        storage[countResumes - 1] = null;
        countResumes--;
    }

    @Override
    protected Resume getResume(int index) {
        return storage[index];
    }

    protected abstract void insertResumeInArray(Resume resume, int index);

    protected abstract void removeResumeInArray(int index);

    protected abstract int getIndex(String uuid);
}

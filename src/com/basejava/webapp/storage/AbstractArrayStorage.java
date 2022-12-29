package com.basejava.webapp.storage;

import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {
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
    public List<Resume> doCopyAll() {
        return Arrays.asList(Arrays.copyOf(storage, countResumes));
    }

    @Override
    protected void doUpdate(Resume resume, Integer searchKey) {
        storage[searchKey] = resume;
    }

    @Override
    protected void doSave(Resume resume, Integer searchKey) throws StorageException {
        if (STORAGE_LIMIT == countResumes) {
            throw new StorageException("Resume database is full", resume.getUuid());
        } else {
            insertResumeInArray(resume, searchKey);
            countResumes++;
        }
    }

    @Override
    protected void doDelete(Integer searchKey) {
        removeResumeInArray(searchKey);
        storage[countResumes - 1] = null;
        countResumes--;
    }

    @Override
    protected Resume doGet(Integer searchKey) {
        return storage[searchKey];
    }

    @Override
    protected boolean isExist(Integer searchKey) {
        return searchKey >= 0;
    }

    protected abstract void insertResumeInArray(Resume resume, int index);

    protected abstract void removeResumeInArray(int index);
}

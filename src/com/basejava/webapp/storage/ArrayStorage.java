package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {

    protected static final int STORAGE_LIMIT = 10000;
    private final Resume[] storage = new Resume[STORAGE_LIMIT];

    private int countResumes = 0;

    private int getIndex(String uuid) {
        for (int i = 0; i < countResumes; i++) {
            if (storage[i].toString().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    public void clear() {
        Arrays.fill(storage, 0, countResumes, null);
        countResumes = 0;
    }

    public void update(Resume resume) {
        if (getIndex(resume.getUuid()) >= 0) {
            storage[getIndex(resume.getUuid())] = resume;
        } else {
            System.err.println("ERROR Unable to update " + resume.getUuid() + ": There is no such resume in database");
        }
    }

    public void save(Resume resume) {
        if (getIndex(resume.getUuid()) >= 0) {
            System.err.println("ERROR Unable to save " + resume.getUuid() + ": Resume already exist in database");
        } else if (storage.length == countResumes) {
            System.err.println("ERROR Unable to save " + resume.getUuid() + ": Resume database is full");
        } else {
            storage[countResumes] = resume;
            countResumes++;
        }
    }

    public Resume get(String uuid) {
        if (getIndex(uuid) < 0) {
            System.err.println("ERROR Unable to get " + uuid + ": There is no such resume in database");
        } else {
            return storage[getIndex(uuid)];
        }
        return null;
    }

    public void delete(String uuid) {
        if (getIndex(uuid) < 0) {
            System.err.println("ERROR Unable to delete " + uuid + ": There is no such resume in database");
        } else {
            storage[getIndex(uuid)] = storage[countResumes - 1];
            storage[countResumes - 1] = null;
            countResumes--;
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, countResumes);
    }

    public int size() {
        return countResumes;
    }
}

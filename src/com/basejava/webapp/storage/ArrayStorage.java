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

    private boolean checkExist(String uuid) {
        for (int i = 0; i < countResumes; i++) {
            if (storage[i].toString().equals(uuid)) {
                return true;
            }
        }
        return false;
    }

    public void clear() {
        Arrays.fill(storage, 0, countResumes, null);
        countResumes = 0;
    }

    public void update(Resume resume) {
        if (checkExist(resume.getUuid())) {
            int indexOfResume = Arrays.asList(getAll()).indexOf(resume);
            storage[indexOfResume] = resume;
        } else {
            System.err.println("ERROR Unable to update " + resume.getUuid() + ": There is no such resume in database");
        }
    }

    public void save(Resume resume) {
        if (checkExist(resume.getUuid())) {
            System.err.println("ERROR Unable to save " + resume.getUuid() + ": Resume already exist in database");
        } else if (storage.length == countResumes) {
            System.err.println("ERROR Unable to save " + resume.getUuid() + ": Resume database is full");
        } else {
            storage[countResumes] = resume;
            countResumes++;
        }
    }

    public Resume get(String uuid) {
        if (!checkExist(uuid)) {
            System.err.println("ERROR Unable to get " + uuid + ": There is no such resume in database");
        } else {
            for (int i = 0; i < countResumes; i++) {
                if (storage[i].toString().equals(uuid)) {
                    return storage[i];
                }
            }
        }
        return null;
    }

    public void delete(String uuid) {
        if (!checkExist(uuid)) {
            System.err.println("ERROR Unable to delete " + uuid + ": There is no such resume in database");
        } else {
            for (int i = 0; i < countResumes; i++) {
                if (storage[i].toString().equals(uuid)) {
                    while (i < countResumes - 1) {
                        storage[i] = storage[++i];
                    }
                    storage[i] = null;
                    countResumes--;
                }
            }
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

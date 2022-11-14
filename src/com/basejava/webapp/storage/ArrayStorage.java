package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private final Resume[] storage = new Resume[10000];
    private final String NOT_EXIST = "There is no such resume in database";
    private final String IS_EXIST = "Resume already exist in database";
    private final String OVERFLOW = "Resume database is full";

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
        for (int i = 0; i < countResumes; i++) {
            storage[i] = null;
        }
        countResumes = 0;
    }

    public void save(Resume resume) {
        if (checkExist(resume.getUuid())) {
            System.err.println("ERROR Unable to save " + resume.getUuid() + ": " + IS_EXIST);
        } else if (storage.length == countResumes) {
            System.err.println("ERROR Unable to save " + resume.getUuid() + ": " + OVERFLOW);
        } else {
            storage[countResumes] = resume;
            countResumes++;
        }
    }

    public Resume get(String uuid) {
        if (!checkExist(uuid)) {
            System.err.println("ERROR Unable to get " + uuid + ": " + NOT_EXIST);
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
            System.err.println("ERROR Unable to delete " + uuid + ": " + NOT_EXIST);
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

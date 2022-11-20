package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10000;

    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int countResumes = 0;

    public int size() {
        return countResumes;
    }

    public void clear() {
        Arrays.fill(storage, 0, countResumes, null);
        countResumes = 0;
    }

    public Resume[] getAll() {
        return Arrays.copyOf(storage, countResumes);
    }

    public void update(Resume resume) {
        if (getIndex(resume.getUuid()) >= 0) {
            storage[getIndex(resume.getUuid())] = resume;
        } else {
            System.err.println("ERROR Unable to update " + resume.getUuid() + ": There is no such resume in database");
        }
    }

    public void save(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index >= 0) {
            System.err.println("ERROR Unable to save " + resume.getUuid() + ": Resume already exist in database");
        } else if (STORAGE_LIMIT == countResumes) {
            System.err.println("ERROR Unable to save " + resume.getUuid() + ": Resume database is full");
        } else {
            add(resume);
        }
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            System.err.println("ERROR Unable to delete " + uuid + ": There is no such resume in database");
        } else {
            remove(index);
        }
    }

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            System.err.println("ERROR Unable to get " + uuid + ": There is no such resume in database");
        } else {
            return storage[index];
        }
        return null;
    }

    protected abstract void add(Resume resume);

    protected abstract void remove(int index);

    protected abstract int getIndex(String uuid);
}

package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    /**
     * return array, contains only Resumes in storage (without null)
     */

    @Override
    protected void insertResumeInArray(Resume resume, int index) {
        storage[countResumes] = resume;
    }

    @Override
    protected void removeResumeInArray(int index) {
        storage[index] = storage[countResumes - 1];
    }

    @Override
    protected Integer getSearchKey(String uuid) {
        for (int i = 0; i < countResumes; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return i;
            }
        }
        return -1;
    }
}

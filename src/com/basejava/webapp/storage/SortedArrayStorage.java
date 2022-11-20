package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected void add(Resume resume) {
        int insertPoint = Math.abs(getIndex(resume.getUuid())) - 1;
        if (insertPoint > countResumes - 1) {
            storage[insertPoint] = resume;
            countResumes++;
        } else {
            Resume temp = storage[insertPoint];
            storage[insertPoint] = resume;
            save(temp);
        }
    }

    @Override
    protected void remove(int index) {
        while (index < countResumes - 1) {
            storage[index] = storage[++index];
        }
        storage[index] = null;
        countResumes--;
    }

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, countResumes, searchKey);
    }
}

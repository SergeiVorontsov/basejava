package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    public void clear() {
        Arrays.fill(storage, 0, countResumes, null);
        countResumes = 0;

    }

    @Override
    public void update(Resume resume) {
    }

    @Override
    public void save(Resume resume) {
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
    public void delete(String uuid) {

    }

    @Override
    public Resume[] getAll() {
        return Arrays.copyOf(storage, countResumes);
    }

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, countResumes, searchKey);
    }
}

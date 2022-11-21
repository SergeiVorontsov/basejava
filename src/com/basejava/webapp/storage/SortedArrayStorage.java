package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected void insertResume(Resume resume) {
        int insertPoint = Math.abs(getIndex(resume.getUuid())) - 1;
        System.arraycopy(storage, insertPoint, storage, insertPoint + 1, countResumes - insertPoint);
        storage[insertPoint] = resume;
    }

    @Override
    protected void removeResume(int index) {
        System.arraycopy(storage, index + 1, storage, index, countResumes - (index + 1));
    }

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, countResumes, searchKey);
    }
}

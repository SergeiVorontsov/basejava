package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected void insertResumeInArray(Resume resume, int index) {
        int insertPoint = Math.abs(index) - 1;
        System.arraycopy(storage, insertPoint, storage, insertPoint + 1, countResumes - insertPoint);
        storage[insertPoint] = resume;
    }

    @Override
    protected void removeResumeInArray(int index) {
        System.arraycopy(storage, index + 1, storage, index, countResumes - (index + 1));
    }

    protected Object getSearchKey(String uuid) {
        Resume searchKey = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, countResumes, searchKey);
    }
}

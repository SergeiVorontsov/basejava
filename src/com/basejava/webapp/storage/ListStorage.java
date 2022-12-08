package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer> {
    protected static final List<Resume> storage = new ArrayList<>();

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public List<Resume> getCopyStorageList() {
        return new ArrayList<>(storage);
    }

    @Override
    public void removeResume(Integer searchKey) {
        storage.remove(storage.get(searchKey));
    }

    @Override
    protected void setResume(Resume resume, Integer searchKey) {
        storage.set(searchKey, resume);
    }

    @Override
    public Resume getResume(Integer searchKey) {
        return storage.get(searchKey);
    }

    @Override
    protected void insertResume(Resume resume, Integer searchKey) {
        storage.add(resume);
    }

    @Override
    protected Integer getSearchKey(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    protected boolean isExist(Integer searchKey) {
        return searchKey >= 0;
    }
}
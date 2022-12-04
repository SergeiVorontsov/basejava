package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
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
    public Resume[] getAll() {
        return storage.toArray(new Resume[0]);
    }

    @Override
    public void removeResume(Object searchKey) {
        storage.remove(storage.get((int) searchKey));
    }

    @Override
    protected void setResume(Resume resume, Object searchKey) {
        storage.set((int) searchKey, resume);
    }

    @Override
    public Resume getResume(Object searchKey) {
        return storage.get((int) searchKey);
    }

    @Override
    protected void insertResume(Resume resume, Object searchKey) {
        storage.add(resume);
    }

    @Override
    protected Object getSearchKey(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    protected boolean isExist(Object searchKey) {
        return (int) searchKey >= 0;
    }
}
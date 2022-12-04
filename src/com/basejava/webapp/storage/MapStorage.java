package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.LinkedHashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {
    protected static final Map<String, Resume> storage = new LinkedHashMap<>();

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
        return storage.values().toArray(new Resume[0]);
    }

    @Override
    public void removeResume(Object searchKey) {
        storage.remove((String) searchKey);
    }

    @Override
    protected void setResume(Resume resume, Object searchKey) {
        storage.put((String) searchKey, resume);
    }

    @Override
    public Resume getResume(Object searchKey) {
        return storage.get((String) searchKey);
    }

    @Override
    protected void insertResume(Resume resume, Object searchKey) {
        storage.put((String) searchKey, resume);
    }

    @Override
    protected Object getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return storage.containsKey((String) searchKey);
    }
}
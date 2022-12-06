package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapResumeStorage extends AbstractStorage {
    protected static final Map<String, Resume> storage = new HashMap<>();

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public List<Resume> getListStorage() {
        return new ArrayList<>(storage.values());
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
        for (Map.Entry<String, Resume> entry : storage.entrySet()) {
            String key = entry.getKey();
            Resume value = entry.getValue();
            if (value.getFullName().equalsIgnoreCase((String) searchKey)) {
                return value;
            }
        }
        return null;
    }

    @Override
    protected void insertResume(Resume resume, Object searchKey) {
        storage.put((String) searchKey, resume);
    }

    @Override
    protected Object getSearchKey(String fullName) {

        for (Map.Entry<String, Resume> entry : storage.entrySet()) {
            String key = entry.getKey();
            Resume value = entry.getValue();
            return value.getFullName().equalsIgnoreCase(fullName);
        }
        return null;
    }

    @Override
    protected boolean isExist(Object fullName) {
        return fullName != null;
    }
}
package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapUuidStorage extends AbstractStorage {
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
    public List<Resume> getAllSorted() {
        List<Resume> listResume = new ArrayList<>(storage.values());
        listResume.sort(resumeComparator);
        return listResume;
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
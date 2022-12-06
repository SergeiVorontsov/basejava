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
        Resume resume = (Resume) searchKey;
        storage.remove(resume.getUuid());
    }

    @Override
    protected void setResume(Resume resume, Object searchKey) {
        Resume oldResume = (Resume) searchKey;
        storage.put(oldResume.getUuid(), resume);
    }

    @Override
    public Resume getResume(Object searchKey) {
        return (Resume) searchKey;
    }

    @Override
    protected void insertResume(Resume resume, Object searchKey) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected Object getSearchKey(String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected boolean isExist(Object resume) {
        return resume != null;
    }
}
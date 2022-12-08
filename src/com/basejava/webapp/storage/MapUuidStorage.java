package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapUuidStorage extends AbstractStorage<String> {
    protected static final Map<String, Resume> mapUuidStorage = new HashMap<>();

    @Override
    public int size() {
        return mapUuidStorage.size();
    }

    @Override
    public void clear() {
        mapUuidStorage.clear();
    }

    @Override
    public List<Resume> getCopyStorageList() {
        return new ArrayList<>(mapUuidStorage.values());
    }

    @Override
    public void removeResume(String searchKey) {
        mapUuidStorage.remove(searchKey);
    }

    @Override
    protected void setResume(Resume resume, String searchKey) {
        mapUuidStorage.put(searchKey, resume);
    }

    @Override
    public Resume getResume(String searchKey) {
        return mapUuidStorage.get(searchKey);
    }

    @Override
    protected void insertResume(Resume resume, String searchKey) {
        mapUuidStorage.put(searchKey, resume);
    }

    @Override
    protected String getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected boolean isExist(String searchKey) {
        return mapUuidStorage.containsKey(searchKey);
    }
}
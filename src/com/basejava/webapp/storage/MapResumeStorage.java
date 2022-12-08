package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapResumeStorage extends AbstractStorage<Resume> {
    protected static final Map<String, Resume> mapResumeStorage = new HashMap<>();

    @Override
    public int size() {
        return mapResumeStorage.size();
    }

    @Override
    public void clear() {
        mapResumeStorage.clear();
    }

    @Override
    public List<Resume> getCopyStorageList() {
        return new ArrayList<>(mapResumeStorage.values());
    }

    @Override
    public void removeResume(Resume searchKey) {
        mapResumeStorage.remove((searchKey).getUuid());
    }

    @Override
    protected void setResume(Resume resume, Resume searchKey) {
        mapResumeStorage.put(searchKey.getUuid(), resume);
    }

    @Override
    public Resume getResume(Resume searchKey) {
        return searchKey;
    }

    @Override
    protected void insertResume(Resume resume, Resume searchKey) {
        mapResumeStorage.put(resume.getUuid(), resume);
    }

    @Override
    protected Resume getSearchKey(String uuid) {
        return mapResumeStorage.get(uuid);
    }

    @Override
    protected boolean isExist(Resume resume) {
        return resume != null;
    }
}
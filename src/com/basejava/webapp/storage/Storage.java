package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

public interface Storage {

    int size();

    void clear();

    Resume[] getAll();

    void update(Resume resume);

    void save(Resume resume);

    void delete(String uuid);

    Resume get(String uuid);
}

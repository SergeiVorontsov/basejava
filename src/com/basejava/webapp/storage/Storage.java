package com.basejava.webapp.storage;

import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;

public interface Storage {

    int size();

    void clear();

    Resume[] getAll();

    void update(Resume resume) throws NotExistStorageException;

    void save(Resume resume) throws StorageException;

    void delete(String uuid) throws NotExistStorageException;

    Resume get(String uuid) throws NotExistStorageException;
}

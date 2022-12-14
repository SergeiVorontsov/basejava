package com.basejava.webapp.storage;

import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;

import java.util.List;

public interface Storage {

    int size();

    void clear();

    List<Resume> getAllSorted();

    void update(Resume resume) throws StorageException;

    void save(Resume resume) throws StorageException;

    void delete(String uuid) throws NotExistStorageException;

    Resume get(String uuid) throws NotExistStorageException;
}
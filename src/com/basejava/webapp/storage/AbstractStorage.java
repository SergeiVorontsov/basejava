package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;

import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractStorage<T> implements Storage {

    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());
    private static final Comparator<Resume> RESUME_COMPARATOR
            = Comparator.nullsLast(Comparator.comparing(Resume::getFullName, String::compareTo)
            .thenComparing(Resume::getUuid, String::compareTo));


    @Override
    public void update(Resume resume) throws StorageException {
        LOG.info("Update " + resume);
        doUpdate(resume, getExistingSearchKey(resume.getUuid()));
    }

    @Override
    public void save(Resume resume) throws StorageException {
        LOG.info("Save " + resume);
        doSave(resume, getNotExistingSearchKey(resume.getUuid()));
    }

    @Override
    public void delete(String uuid) throws NotExistStorageException {
        LOG.info("Delete " + uuid);
        doDelete(getExistingSearchKey(uuid));
    }

    @Override
    public Resume get(String uuid) throws NotExistStorageException {
        LOG.info("Get " + uuid);
        return getResume(getExistingSearchKey(uuid));
    }

    private T getExistingSearchKey(String uuid) throws NotExistStorageException {
        T searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            LOG.warning("Resume " + uuid + " not exist");
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    private T getNotExistingSearchKey(String uuid) throws ExistStorageException {
        T searchKey = getSearchKey(uuid);
        if (isExist(searchKey)) {
            LOG.warning("Resume " + uuid + " already exist");
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }

    public List<Resume> getAllSorted() {
        LOG.info("getAllSorted");
        List<Resume> list = doCopyAll();
        list.sort(RESUME_COMPARATOR);
        return list;
    }

    public abstract int size();

    public abstract void clear();

    protected abstract List<Resume> doCopyAll();

    protected abstract void doSave(Resume resume, T searchKey) throws StorageException;

    protected abstract void doDelete(T searchKey);

    protected abstract void doUpdate(Resume resume, T searchKey) throws StorageException;

    protected abstract Resume getResume(T searchKey);

    protected abstract T getSearchKey(String uuid);

    protected abstract boolean isExist(T searchKey);
}
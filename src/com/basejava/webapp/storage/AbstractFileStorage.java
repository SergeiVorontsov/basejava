package com.basejava.webapp.storage;

import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File> {
    private final File directory;

    protected AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory, "directory must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + "is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + "is not readable/writable");
        }
        this.directory = directory;
    }

    @Override
    public int size() {
        return checkedListFiles(directory).length;
    }

    @Override
    public void clear() {
        for (File file : checkedListFiles(directory)) {
            doDelete(file);
        }
    }

    @Override
    protected List<Resume> doCopyAll() {
        List<Resume> list = new ArrayList<>();
        for (File file : checkedListFiles(directory)) {
            list.add(doGet(file));
        }
        return list;
    }

    @Override
    protected void doSave(Resume resume, File file) throws StorageException {
        try {
            file.createNewFile();
            doWrite(resume, new BufferedOutputStream(new FileOutputStream(file)));
        } catch (IOException e) {
            throw new StorageException("Couldn't create file" + file.getAbsolutePath(), file.getName(), e);
        }
    }

    @Override
    protected void doDelete(File file) {
        if (!file.delete()) {
            throw new StorageException("Failed to delete file", file.getName());
        }
    }

    @Override
    protected void doUpdate(Resume resume, File file) throws StorageException {
        try {
            doWrite(resume, new BufferedOutputStream(new FileOutputStream(file)));
        } catch (IOException e) {
            throw new StorageException("File write error", resume.getUuid(), e);
        }
    }

    @Override
    protected Resume doGet(File file) {
        try {
            return doRead(new BufferedInputStream(new FileInputStream(file)));
        } catch (Exception e) {
            throw new StorageException("File read error", file.getName(), e);
        }
    }

    @Override
    protected File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected boolean isExist(File file) {
        return file.exists();
    }

    public File[] checkedListFiles(File directory) {
        File[] files = directory.listFiles();
        if (files == null) {
            throw new StorageException("IO error", directory.getName());
        }
        return files;
    }

    protected abstract void doWrite(Resume resume, OutputStream file) throws IOException;

    protected abstract Resume doRead(InputStream file) throws IOException;
}
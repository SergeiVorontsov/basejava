package com.basejava.webapp.storage;

import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File> {
    private final File directory;

    protected AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory, "directory must not be null");
        if (directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + "is not directory");
        }
        if (directory.canRead() || directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + "is not readable/writable");
        }
        this.directory = directory;
    }

    @Override
    public int size() {
        int count = 0;
        for (File file : getListFile(directory, new ArrayList<>())) {
            if (file.isFile()) {
                count++;
            }
        }
        return count;
    }

    @Override
    public void clear() {
        List<File> list = new ArrayList<>();
        for (File file : getListFile(directory, list)) {
            if (file.isFile()) {
                removeResume(file);
            }
        }
        for (File file : getListFile(directory, list)) {
            removeResume(file);
        }
    }

    @Override
    protected List<Resume> getCopyStorageList() {
        return null;
    }

    @Override
    protected void insertResume(Resume resume, File file) throws StorageException {
        try {
            Files.createFile(file.toPath());
            doWrite(resume, file);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    private void doWrite(Resume resume, File file) throws IOException {
    }

    @Override
    protected void removeResume(File file) {
        try {
            Files.delete(file.toPath());
        } catch (NoSuchFileException e) {
            throw new RuntimeException("Failed to delete the file!" + file.getName());
        } catch (DirectoryNotEmptyException e) {
            throw new RuntimeException("Directory is not full!" + file.getName());
        } catch (IOException e) {
            throw new RuntimeException("IO error" + file.getName(), e);
        }
    }

    @Override
    protected void setResume(Resume resume, File file) throws StorageException {
        try {
            doWrite(resume, file);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    protected Resume getResume(File file) {
        try {
            return doRead(file);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Resume doRead(File file) {
        return null;
    }

    @Override
    protected File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected boolean isExist(File file) {
        return file.exists();
    }

    public List<File> getListFile(File directory, List<File> list) {
        File[] files = directory.listFiles();
        if (files != null)
            for (File file : files) {
                list.add(file);
                if (file.isDirectory()) {
                    getListFile(new File(file.getPath()), list);
                }
            }
        return list;
    }
}

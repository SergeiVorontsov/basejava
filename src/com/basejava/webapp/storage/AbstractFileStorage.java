package com.basejava.webapp.storage;

import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        try {
            return getAllFilesFrom(directory).size();
        } catch (StorageException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void clear() {
        try (Stream<Path> filesWalk = Files.walk(directory.toPath(), Integer.MAX_VALUE)) {
            filesWalk
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(this::doDelete);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected List<Resume> doCopyAll() {
        List<Resume> list = new ArrayList<>();
        try {
            for (File file : getAllFilesFrom(directory)) {
                list.add(doRead(file));
            }
        } catch (StorageException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    protected void doSave(Resume resume, File file) throws StorageException {
        try {
            if (!file.createNewFile()) {
                throw new IOException("Failed to create new" + file.getName());
            }
            doWrite(resume, file);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    protected void doDelete(File file) {
        try {
            if (!file.delete()) {
                throw new IOException("Failed to delete file" + file.getName());
            }
        } catch (IOException e) {
            throw new RuntimeException("IO error" + file.getName(), e);
        }
    }

    @Override
    protected void doUpdate(Resume resume, File file) throws StorageException {
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

    @Override
    protected File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected boolean isExist(File file) {
        return file.exists();
    }

    public List<File> getAllFilesFrom(File directory) throws StorageException {
        List<File> filesInFolders = null;
        try (Stream<Path> filesWalk = Files.walk(directory.toPath(), Integer.MAX_VALUE)) {
            filesInFolders = filesWalk
                    .filter(Files::isRegularFile)
                    .map(Path::toFile)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new StorageException("IO error", directory.getName(), e);
        }
        return filesInFolders;
    }

    public List<File> getAllFilesFrom2(File directory, List<File> list) {
        File[] arrayOfFiles = directory.listFiles();
        assert arrayOfFiles != null;
        for (File file : arrayOfFiles) {
            if (file.isFile()) {
                list.add(file);
            } else {
                getAllFilesFrom2(file,list);
            }
        }
        return list;
    }


    protected abstract void doWrite(Resume resume, File file) throws IOException;

    protected abstract Resume doRead(File file);
}

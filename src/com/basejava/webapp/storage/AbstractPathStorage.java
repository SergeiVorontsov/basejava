package com.basejava.webapp.storage;

import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class AbstractPathStorage extends AbstractStorage<Path> {
    private final Path directory;

    protected AbstractPathStorage(String dir) {
        directory = Paths.get(dir);
        Objects.requireNonNull(directory, "directory must not be null");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + "is not directory or is not writable");
        }
    }

    @Override
    public int size() {
        return checkedListPaths(directory).size();
    }

    @Override
    public void clear() {
        checkedListPaths(directory).forEach(this::doDelete);
    }

    @Override
    protected List<Resume> doCopyAll() {
        List<Resume> list = new ArrayList<>();
        checkedListPaths(directory).forEach(path -> list.add(doGet(path)));
        return list;
    }

    @Override
    protected void doSave(Resume resume, Path path) throws StorageException {
        try {
            Files.createFile(path);
            doWrite(resume, new BufferedOutputStream(Files.newOutputStream(path)));
        } catch (IOException e) {
            throw new StorageException("Path write error" + resume.getUuid(), path.toString(), e);
        }
    }

    @Override
    protected void doDelete(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new StorageException("Path delete error ", directory.toString(), e);
        }
    }

    @Override
    protected void doUpdate(Resume resume, Path path) throws StorageException {
        try {
            doWrite(resume, new BufferedOutputStream(Files.newOutputStream(path)));
        } catch (IOException e) {
            throw new StorageException("Path write error", resume.getUuid(), e);
        }
    }

    @Override
    protected Resume doGet(Path path) {
        try {
            return doRead(new BufferedInputStream(Files.newInputStream(path)));
        } catch (Exception e) {
            throw new StorageException("Path read error", path.toString(), e);
        }
    }

    @Override
    protected Path getSearchKey(String path) {
        return Paths.get(directory.toString(), path);
    }

    @Override
    protected boolean isExist(Path path) {
        return Files.exists(path);
    }

    public List<Path> checkedListPaths(Path directory) {
        try (Stream<Path> pathStream = Files.list(directory)) {
            return pathStream.collect(Collectors.toList());
        } catch (IOException e) {
            throw new StorageException("Path IO exception", directory.toString(), e);
        }
    }

    protected abstract void doWrite(Resume resume, OutputStream path) throws IOException;

    protected abstract Resume doRead(InputStream path) throws IOException;
}
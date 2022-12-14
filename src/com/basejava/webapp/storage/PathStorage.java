package com.basejava.webapp.storage;

import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;
import com.basejava.webapp.storage.SerializeStrategy.Serializer;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PathStorage extends AbstractStorage<Path> {
    private final Path directory;
    private Serializer serializer;

    protected PathStorage(String dir, Serializer serializer) {
        directory = Paths.get(dir);
        Objects.requireNonNull(directory, "directory must not be null");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + "is not directory or is not writable");
        }
        this.serializer = serializer;
    }

    public void setSerializer(Serializer serializer) {
        this.serializer = serializer;
    }

    @Override
    public int size() {
        return (int)getFilesList().count();
    }

    @Override
    public void clear() {
        getFilesList().forEach(this::doDelete);
    }

    @Override
    protected List<Resume> doCopyAll() {
        return getFilesList().map(this::doGet).collect(Collectors.toList());
    }

    @Override
    protected void doSave(Resume resume, Path path) throws StorageException {
        try {
            Files.createFile(path);
        } catch (IOException e) {
            throw new StorageException("Path write error" + resume.getUuid(), path.toString(), e);
        }
        doUpdate(resume,path);
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
            this.serializer.doWrite(resume, new BufferedOutputStream(Files.newOutputStream(path)));
        } catch (IOException e) {
            throw new StorageException("Path write error", resume.getUuid(), e);
        }
    }

    @Override
    protected Resume doGet(Path path) {
        try {
            return this.serializer.doRead(new BufferedInputStream(Files.newInputStream(path)));
        } catch (Exception e) {
            throw new StorageException("Path read error", path.toString(), e);
        }
    }

    @Override
    protected Path getSearchKey(String path) {
        return directory.resolve(path);
    }

    @Override
    protected boolean isExist(Path path) {
        return Files.exists(path);
    }

    private Stream<Path> getFilesList() {
        try {
            return Files.list(directory);
        } catch (IOException e) {
            throw new StorageException("Path IO exception", directory.toString(), e);
        }
    }
}
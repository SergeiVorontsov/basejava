package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static com.basejava.webapp.storage.AbstractArrayStorage.STORAGE_LIMIT;

public class ArrayStorageTest extends AbstractArrayStorageTest {
    private static final Storage storage = new ArrayStorage();

    public ArrayStorageTest() {
        super(storage);
    }

    @Before
    public void setUp() throws StorageException {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
    }

    @Test
    public void size() {
        Assert.assertEquals(3, storage.size());
    }

    @Test
    public void clear() {
    }

    @Test
    public void getAll() {
    }

    @Test
    public void update() {
    }


    @Test
    public void delete() {
    }

    @Test
    public void get() {
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws NotExistStorageException {
        storage.get("dummy");
    }

    @Test(expected = ExistStorageException.class)
    public void getExist() throws StorageException {
        storage.save(new Resume(UUID_1));
    }

    @Test(expected = StorageException.class)
    public void getOverflow() throws StorageException {
        try {
            for (int i = 3; i < STORAGE_LIMIT; i++) {
                storage.save(new Resume());
            }
        } catch (StorageException e) {
            Assert.fail("Database overflow is to early");
        }
        storage.save(new Resume());
    }
}
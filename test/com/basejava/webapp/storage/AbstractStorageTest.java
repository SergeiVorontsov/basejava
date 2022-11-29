package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public abstract class AbstractStorageTest {
    public static final String UUID_1 = "uuid1";
    public static final String UUID_2 = "uuid2";
    public static final String UUID_3 = "uuid3";
    public static final String UUID_4 = "uuid4";
    public static final Resume RESUME_1 = new Resume(UUID_1);
    public static final Resume RESUME_2 = new Resume(UUID_2);
    public static final Resume RESUME_3 = new Resume(UUID_3);
    public static final Resume RESUME_4 = new Resume(UUID_4);
    public static final String UUID_NOT_EXIST = "dummy";
    protected final Storage storage;

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws StorageException {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    public void testSize() {
        assertSize(3);
    }

    @Test
    public void testClear() {
        storage.clear();
        assertSize(0);
        Assert.assertArrayEquals(new Resume[0], storage.getAll());
    }

    @Test
    public void testGetAll() {
        Resume[] expected = {RESUME_1, RESUME_2, RESUME_3};
        Assert.assertArrayEquals(expected, storage.getAll());
        assertSize(3);
    }

    @Test
    public void testGetAllNotNull() {
        for (Resume resume : storage.getAll()) {
            Assert.assertNotNull(resume);
        }
    }

    @Test
    public void testUpdate() throws NotExistStorageException {
        Resume tempResume = new Resume(UUID_1);
        storage.update(tempResume);
        Assert.assertSame(tempResume, storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void testUpdateNotExist() throws NotExistStorageException {
        storage.update(new Resume());
    }

    @Test
    public void testSave() throws StorageException {
        storage.save(RESUME_4);
        assertGet(RESUME_4);
        assertSize(4);
    }

    @Test(expected = ExistStorageException.class)
    public void testSaveExist() throws StorageException {
        storage.save(new Resume(UUID_1));
    }

    @Test
    public void testDelete() throws NotExistStorageException {
        storage.delete(UUID_1);
        try {
            storage.get(UUID_1);
            Assert.fail("Resume is not deleted from storage");
        } catch (NotExistStorageException e) {
            Assert.assertNotNull(e);
        }
        assertSize(2);
    }

    @Test(expected = NotExistStorageException.class)
    public void testDeleteNotExist() throws NotExistStorageException {
        storage.delete(UUID_NOT_EXIST);
    }

    @Test
    public void testGet() throws StorageException {
        assertGet(RESUME_1);
        assertGet(RESUME_2);
        assertGet(RESUME_3);
    }

    @Test(expected = NotExistStorageException.class)
    public void testGetNotExist() throws NotExistStorageException {
        storage.get(UUID_NOT_EXIST);
    }

    private void assertSize(int expected) {
        Assert.assertEquals(expected, storage.size());
    }

    private void assertGet(Resume resume) throws NotExistStorageException {
        storage.get(resume.getUuid());
        Assert.assertSame(storage.get(resume.getUuid()), resume);
    }
}
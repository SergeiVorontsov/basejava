package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;

import static com.basejava.webapp.storage.AbstractArrayStorage.STORAGE_LIMIT;


public abstract class AbstractArrayStorageTest {
    public static final String UUID_1 = "uuid1";
    public static final String UUID_2 = "uuid2";
    public static final String UUID_3 = "uuid3";
    private final Storage storage;

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
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
    public void clear() throws NoSuchFieldException, IllegalAccessException {
        storage.clear();
        Field field = storage.getClass().getSuperclass().getDeclaredField("storage");
        field.setAccessible(true);
        Resume[] fieldValue = (Resume[]) field.get(storage);
        for (Resume resume : fieldValue) {
            Assert.assertNull(resume);
        }
        field.setAccessible(false);
    }

    @Test
    public void getAll() {
        Resume[] testResumeArr = storage.getAll();
        Assert.assertEquals(3, testResumeArr.length);
        for (Resume resume : testResumeArr) {
            Assert.assertNotNull(resume);
        }
    }

    @Test
    public void getAllNotNull() {
        Resume[] testResumeArr = storage.getAll();
        for (Resume resume : testResumeArr) {
            Assert.assertNotNull(resume);
        }
    }

    @Test
    public void update() throws StorageException, NoSuchFieldException, IllegalAccessException {
        Resume testResume = new Resume(UUID_1);
        Field field = storage.getClass().getSuperclass().getDeclaredField("storage");
        field.setAccessible(true);
        Resume[] currentStorage = (Resume[]) field.get(storage);
        Resume currentResume = currentStorage[0];
        field.setAccessible(false);
        storage.update(testResume);
        Assert.assertEquals(testResume, currentResume);

    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() throws NotExistStorageException {
        storage.update(new Resume());
    }

    @Test
    public void save() throws StorageException {
        storage.save(new Resume());
        Assert.assertEquals(4, storage.size());
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() throws StorageException {
        storage.save(new Resume(UUID_1));
    }

    @Test(expected = StorageException.class)
    public void saveOverflow() throws StorageException {
        try {
            for (int i = 3; i < STORAGE_LIMIT; i++) {
                storage.save(new Resume());
            }
        } catch (StorageException e) {
            Assert.fail("Database overflow is to early");
        }
        storage.save(new Resume());
    }

    @Test
    public void delete() throws NotExistStorageException {
        storage.delete(UUID_1);
        try {
            storage.get(UUID_1);
            Assert.fail("Resume is not deleted from storage");
        } catch (NotExistStorageException e) {
            Assert.assertNotNull(e);
        }
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() throws NotExistStorageException {
        storage.delete("dummy");
    }

    @Test
    public void get() throws StorageException {
        Resume testResume = new Resume();
        storage.save(testResume);
        Assert.assertSame(storage.get(testResume.getUuid()), testResume);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws NotExistStorageException {
        storage.get("dummy");
    }
}
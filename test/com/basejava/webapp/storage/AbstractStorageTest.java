package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public abstract class AbstractStorageTest {
    protected static final File STORAGE_DIR = new File("C:\\Users\\HTPC\\IdeaProjects\\basejava\\storage");
    protected final Storage storage;
    public static final String UUID_1 = "uuid1";
    public static final String UUID_2 = "uuid2";
    public static final String UUID_3 = "uuid3";
    public static final String UUID_4 = "uuid4";
    public static final String FULLNAME_1 = "Bob";
    public static final String FULLNAME_2 = "Jack";
    public static final String FULLNAME_3 = "Tom";
    public static final String FULLNAME_4 = "Mary";
    public static final Resume RESUME_1;
    public static final Resume RESUME_2;
    public static final Resume RESUME_3;
    public static final Resume RESUME_4;
    public static final String UUID_NOT_EXIST = "dummy";

    static {
        RESUME_1 = new Resume(UUID_1, FULLNAME_1);
        RESUME_1.setContact(ContactType.PHONE, "777-77-77");
        RESUME_1.setContact(ContactType.EMAIL, "info@gmail.com");
        RESUME_1.setSection(SectionType.OBJECTIVE, new TextSection("Objective1"));
        RESUME_1.setSection(SectionType.PERSONAL, new TextSection("Objective1"));
        RESUME_1.setSection(SectionType.ACHIEVEMENT, new ListSection("Achievement1", "Achievement2", "Achievement3"));
        RESUME_1.setSection(SectionType.EXPERIENCE, new CompanySection(
                new Company("Company Name", "www.site.com",
                        new Company.Period("Position", "Description", "05.2020", "06.2020"),
                        new Company.Period("Position1", "Description1", "05.2021", "06.2021"),
                        new Company.Period("Position2", "Description2", "05.2022")
                ))
        );
        RESUME_2 = new Resume(UUID_2, FULLNAME_2);
        RESUME_2.setContact(ContactType.PHONE, "777-77-78");
        RESUME_2.setContact(ContactType.EMAIL, "mail@gmail.com");
        RESUME_2.setSection(SectionType.OBJECTIVE, new TextSection("Objective2"));
        RESUME_2.setSection(SectionType.PERSONAL, new TextSection("Objective2"));
        RESUME_2.setSection(SectionType.ACHIEVEMENT, new ListSection("Achievement2.1", "Achievement2.2", "Achievement2.3"));
        RESUME_2.setSection(SectionType.EXPERIENCE, new CompanySection(
                new Company("Company Name2", "www.site2.com",
                        new Company.Period("Position1", "Description1", "04.2020", "07.2020"),
                        new Company.Period("Position2.1", "Description2.1", "04.2021", "07.2021"),
                        new Company.Period("Position3.2", "Description3.2", "04.2022", "07.2022")
                ))
        );
        RESUME_3 = new Resume(UUID_3, FULLNAME_3);
        RESUME_4 = new Resume(UUID_4, FULLNAME_4);
    }

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
        Assert.assertEquals(storage.getAllSorted(), Collections.emptyList());
    }

    @Test
    public void testGetAllSorted() {
        List<Resume> expected = Arrays.asList(RESUME_1, RESUME_2, RESUME_3);
        Assert.assertEquals(expected, storage.getAllSorted());
        assertSize(3);
    }

    @Test
    public void testGetAllNotNull() {
        for (Resume resume : storage.getAllSorted()) {
            Assert.assertNotNull(resume);
        }
    }

    @Test
    public void testUpdate() throws StorageException {
        Resume tempResume = new Resume(UUID_1, FULLNAME_4);
        storage.update(tempResume);
        assertEquals(tempResume, storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void testUpdateNotExist() throws StorageException {
        storage.update(new Resume("Random Fullname"));
    }

    @Test
    public void testSave() throws StorageException {
        storage.save(RESUME_4);
        assertGet(RESUME_4);
        assertSize(4);
    }

    @Test(expected = ExistStorageException.class)
    public void testSaveExist() throws StorageException {
        storage.save(new Resume(UUID_1, FULLNAME_1));
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
        Assert.assertEquals(resume, storage.get(resume.getUuid()));
    }
}
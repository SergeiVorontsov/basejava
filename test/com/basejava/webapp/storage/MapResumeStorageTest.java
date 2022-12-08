package com.basejava.webapp.storage;

public class MapResumeStorageTest extends AbstractStorageTest {
    private static final Storage storage = new MapResumeStorage();

    public MapResumeStorageTest() {
        super(storage);
    }


/*    private void assertGet(Resume resume) throws NotExistStorageException {
        storage.get(resume.getUuid());
        Assert.assertSame(storage.get(resume.getUuid()), resume);
    }*/
}
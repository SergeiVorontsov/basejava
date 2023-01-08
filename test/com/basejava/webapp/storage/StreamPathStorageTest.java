package com.basejava.webapp.storage;

import com.basejava.webapp.storage.SerializeStrategy.ObjectStreamSerializer;

public class StreamPathStorageTest extends AbstractStorageTest {

    public StreamPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getPath(), new ObjectStreamSerializer()));
    }
}
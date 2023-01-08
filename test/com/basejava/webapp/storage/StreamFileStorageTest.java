package com.basejava.webapp.storage;

import com.basejava.webapp.storage.SerializeStrategy.ObjectStreamSerializer;

public class StreamFileStorageTest extends AbstractStorageTest {

    public StreamFileStorageTest() {
        super(new FileStorage(STORAGE_DIR, new ObjectStreamSerializer()));
    }
}
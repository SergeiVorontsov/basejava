package com.basejava.webapp.storage;

import com.basejava.webapp.storage.SerializeStrategy.DataStreamSerializer;

public class DataPathStorageTest extends AbstractStorageTest {
    public DataPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getPath(), new DataStreamSerializer()));
    }
}
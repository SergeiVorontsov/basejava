package com.basejava.webapp.storage;

import com.basejava.webapp.storage.SerializeStrategy.JsonStreamSerializer;

public class JsonPathStorageTest extends AbstractStorageTest {
    public JsonPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getPath(), new JsonStreamSerializer()));
    }
}
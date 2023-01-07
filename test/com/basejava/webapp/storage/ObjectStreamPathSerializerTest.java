package com.basejava.webapp.storage;

import com.basejava.webapp.storage.SerializeStrategy.ObjectStreamSerializer;

public class ObjectStreamPathSerializerTest extends AbstractStorageTest {

    public ObjectStreamPathSerializerTest() {
        super(new PathStorage(STORAGE_DIR.getPath(), new ObjectStreamSerializer()));
    }
}
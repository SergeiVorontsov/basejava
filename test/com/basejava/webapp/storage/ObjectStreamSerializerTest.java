package com.basejava.webapp.storage;

import com.basejava.webapp.storage.SerializeStrategy.ObjectStreamSerializer;

public class ObjectStreamSerializerTest extends AbstractStorageTest {

    public ObjectStreamSerializerTest() {
        super(new FileStorage(STORAGE_DIR, new ObjectStreamSerializer()));
    }
}
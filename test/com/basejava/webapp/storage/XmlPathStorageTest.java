package com.basejava.webapp.storage;

import com.basejava.webapp.storage.SerializeStrategy.XmlStreamSerializer;

public class XmlPathStorageTest extends AbstractStorageTest{

    public XmlPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getPath(), new XmlStreamSerializer()));
    }
}

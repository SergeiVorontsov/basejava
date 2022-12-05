package com.basejava.webapp.storage;

public class MapUuidStorageTest extends AbstractStorageTest {
    private static final Storage storage = new MapUuidStorage();

    public MapUuidStorageTest() {
        super(storage);
    }
}
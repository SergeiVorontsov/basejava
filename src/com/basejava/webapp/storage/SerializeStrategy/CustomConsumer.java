package com.basejava.webapp.storage.SerializeStrategy;

import java.io.IOException;

@FunctionalInterface
public interface CustomConsumer<K> {
    void accept(K k) throws IOException;
}
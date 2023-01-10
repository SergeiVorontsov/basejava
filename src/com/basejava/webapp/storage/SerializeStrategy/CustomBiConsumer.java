package com.basejava.webapp.storage.SerializeStrategy;

import java.io.IOException;

@FunctionalInterface
public interface CustomBiConsumer<K,V> {
    void accept(K k,V v) throws IOException;
}
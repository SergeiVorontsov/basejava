package com.basejava.webapp.storage.SerializeStrategy;

import java.io.IOException;

@FunctionalInterface
public interface Consumer<K> {
    void accept(K k) throws IOException;
}
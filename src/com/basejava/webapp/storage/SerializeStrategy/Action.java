package com.basejava.webapp.storage.SerializeStrategy;

import java.io.IOException;
@FunctionalInterface
public interface Action {
        void accept() throws IOException;
}

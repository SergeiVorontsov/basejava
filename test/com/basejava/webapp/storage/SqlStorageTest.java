package com.basejava.webapp.storage;

import com.basejava.webapp.Config;
import com.basejava.webapp.sql.SqlHelper;

public class SqlStorageTest extends AbstractStorageTest {
    public SqlStorageTest() {
        super(new SqlStorage(new SqlHelper(Config.get().getDbUrl(), Config.get().getUser(), Config.get().getPassword())));
    }
}

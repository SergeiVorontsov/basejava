package com.basejava.webapp;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Properties;

public class Config {
    protected static final File PROPS = new File(".\\config\\resumes.properties");
    private static final Config INSTANCE = new Config();

    private final Properties props = new Properties();
    private final File storageDir;
    private final String dbUrl;
    private final String user;
    private final String password;

    public static Config get() {
        return INSTANCE;
    }

    private Config() {
        try (InputStream is = Files.newInputStream(PROPS.toPath())) {
            props.load(is);
            storageDir = new File(props.getProperty("storage.dir"));
            dbUrl = props.getProperty("db.url");
            user = props.getProperty("db.user");
            password = props.getProperty("db.password");
        } catch (IOException e) {
            throw new IllegalStateException(("Invalid config file " + PROPS.getAbsolutePath()));
        }
    }

    public File getStorageDir() {
        return storageDir;
    }

    public String getDbUrl() {
        return dbUrl;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }
}

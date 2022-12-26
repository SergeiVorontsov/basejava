package com.basejava.webapp;

import java.io.File;
import java.util.Objects;

public class MainFile {
    public static void main(String[] args) {
        MainFile mainFile = new MainFile();
        File directory = new File("./src/com/basejava");
        mainFile.getAllFileNames(directory);


    }

    public void getAllFileNames(File directory) {
        for (File file : Objects.requireNonNull(directory.listFiles())) {
            if (file.isFile()) {
                System.out.println(file.getName());
            }
            if (file.isDirectory()) {
                getAllFileNames(new File(file.getPath()));
            }
        }
    }
}
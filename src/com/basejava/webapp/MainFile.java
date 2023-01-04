package com.basejava.webapp;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Objects;
import java.util.stream.Stream;

public class MainFile {
    public static void main(String[] args) {
        MainFile mainFile = new MainFile();
        File directory = new File("./src/com/basejava");

        mainFile.getAllFileNamesIn(directory);

        mainFile.getAllFileNames2In(directory);
    }


    public void getAllFileNamesIn(File directory) {
        int counter = 0;
        recursionSearchFiles(directory, counter);
    }

    private void recursionSearchFiles(File directory, int counter) {
        for (File file : Objects.requireNonNull(directory.listFiles())) {
            if (file.isFile()) {
                String spase = String.join("", Collections.nCopies(counter, "\t"));
                System.out.println(spase + "File: " + file.getName());
            }
            if (file.isDirectory()) {
                String spase = String.join("", Collections.nCopies(counter, "\t"));
                System.out.println(spase + "Directory: " + file.getName());
                int localCount = counter;
                localCount++;
                recursionSearchFiles(file, localCount);
            }
        }
    }

    public void getAllFileNames2In(File directory) {
        try (Stream<Path> stream = Files.walk(directory.toPath())) {
            stream.forEach(x -> {
                long countDir = directory.getPath().chars().filter(ch -> ch == '\\').count();
                long countCur = x.toString().chars().filter(ch -> ch == '\\').count();
                int result = (int) (countCur - countDir);
                String spase = String.join("", Collections.nCopies(result, "\t"));
                if (Files.isDirectory(x)) {
                    System.out.println(spase + "Directory: " + x.getFileName());
                } else {
                    System.out.println(spase + "File: " + x.getFileName());
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
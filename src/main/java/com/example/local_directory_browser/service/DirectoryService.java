package com.example.local_directory_browser.service;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class DirectoryService {

    private final static String BASE = "";

    public List<String> listFilesRecursively(String directoryPath) throws FileNotFoundException {
        List<String> files = new ArrayList<>();
        File directory = new File(directoryPath);

        if (directory.exists() && directory.isDirectory()) {
            browseDirectory(directory, files);
        } else {
            throw new FileNotFoundException(directoryPath);
        }

        return files;
    }

    private void browseDirectory(File directory, List<String> files) {
        File[] fileList = directory.listFiles();

        if (fileList != null) {
            for (File file : fileList) {
                if (file.isFile()) {
                    files.add("File: " + file.getAbsolutePath());
                } else if (file.isDirectory()) {
                    files.add("Directory: " + file.getAbsolutePath());
                    browseDirectory(file, files); // Recursively call for sub-directories
                }
            }
        }
    }
}

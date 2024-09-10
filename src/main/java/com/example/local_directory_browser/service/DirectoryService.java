package com.example.local_directory_browser.service;

import com.example.local_directory_browser.model.DirectoryDTO;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class DirectoryService {

    private final static String BASE = "";

    public DirectoryDTO listFilesRecursively(String directoryPath) throws FileNotFoundException {
        DirectoryDTO files = new DirectoryDTO(directoryPath);
        File directory = new File(directoryPath);

        if (directory.exists() && directory.isDirectory()) {
            browseDirectory(directory, files);
        } else {
            throw new FileNotFoundException("File.exists(): " + directoryPath);
        }
        return files;
    }

    private void browseDirectory(File directory, DirectoryDTO files) throws FileNotFoundException {
        File[] fileList = directory.listFiles();

        if (fileList != null) {
            for (File file : fileList) {
                if (file.isFile()) {
                    files.addFile(file.getName(), file.getAbsolutePath());
                } else if (file.isDirectory()) {
                    files.addDirectory(file.getAbsolutePath());
                }
            }
        } else {
            throw new FileNotFoundException("File.listFiles(): " + directory.getAbsolutePath());
        }
    }
}

package com.example.local_directory_browser.service;

import com.example.local_directory_browser.model.DirectoryDTO;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

@Service
public class DirectoryService {

    private final static String BASE = "/Volumes/Andys_SSD";
    private final static List<String> ALLOWED_FILES =
            Arrays.asList(".mp4", ".mov", ".pdf", ".jpg", ".jpeg", ".png", ".mp3", ".mkv", ".srt");

    public DirectoryDTO listFilesRecursively(String directoryPath) throws FileNotFoundException {
        if (directoryPath == null || directoryPath.isEmpty()) {
            directoryPath = BASE;
        } else {
            directoryPath = BASE + directoryPath;
        }

        DirectoryDTO files = new DirectoryDTO(directoryPath);
        File directory = new File(directoryPath);

        if (directory.exists() && directory.isDirectory()) {
            browseDirectory(directory, files);
        } else {
            throw new FileNotFoundException("File.exists(): " + directoryPath.replace(BASE, ""));
        }
        return files;
    }

    private void browseDirectory(File directory, DirectoryDTO files) throws FileNotFoundException {
        File[] fileList = directory.listFiles();

        if (fileList != null) {
            for (File file : fileList) {
                if (file.isFile() && ALLOWED_FILES.contains(normalizeExtension(file.getName()))) {
                    files.addFile(file.getName(), file.getAbsolutePath());
                } else if (file.isDirectory()) {
                    files.addDirectory(file.getAbsolutePath().replace(BASE, ""));
                }
            }
        } else {
            throw new FileNotFoundException("File.listFiles(): " +
                    directory.getAbsolutePath().replace(BASE, ""));
        }
    }

    private String normalizeExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
    }
}

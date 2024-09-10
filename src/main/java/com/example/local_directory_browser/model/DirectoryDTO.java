package com.example.local_directory_browser.model;

import java.util.ArrayList;
import java.util.List;

public class DirectoryDTO {

    private String path;
    private List<FileDTO> files;
    private List<String> directories;

    public DirectoryDTO(String path) {
        this.path = path;
        files = new ArrayList<>();
        directories = new ArrayList<>();
    }

    public void addFile(String name, String path) {
        files.add(new FileDTO(name, path));
    }
    public void addDirectory(String directory) {
        directories.add(directory);
    }

    // Setters and Getters
    public String getPath() {
        return path;
    }
    public List<FileDTO> getFiles() {
        return files;
    }
    public List<String> getDirectories() {
        return directories;
    }
}

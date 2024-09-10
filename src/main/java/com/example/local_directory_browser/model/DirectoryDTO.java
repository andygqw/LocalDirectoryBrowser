package com.example.local_directory_browser.model;

import java.util.List;

public class DirectoryDTO {

    private String path;
    private List<String> files;
    private List<String> directories;

    public DirectoryDTO(String path) {
        this.path = path;
    }

    // Setters and Getters
    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }
    public List<String> getFiles() {
        return files;
    }
    public void setFiles(List<String> files) {
        this.files = files;
    }
    public List<String> getDirectories() {
        return directories;
    }
    public void setDirectories(List<String> directories) {
        this.directories = directories;
    }
}

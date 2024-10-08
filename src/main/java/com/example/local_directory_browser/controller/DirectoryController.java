package com.example.local_directory_browser.controller;

import com.example.local_directory_browser.model.DirectoryDTO;
import com.example.local_directory_browser.service.DirectoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.io.FileNotFoundException;

@RestController
@RequestMapping(value="/browse")
@CrossOrigin(origins = "*")
public class DirectoryController {

    private final DirectoryService directoryService;

    @Autowired
    public DirectoryController(DirectoryService directoryService) {
        this.directoryService = directoryService;
    }

    @GetMapping
    public ResponseEntity<DirectoryDTO> browseDirectory(@RequestParam(value="path",required = false) String path) throws FileNotFoundException {
        return new ResponseEntity<>(directoryService.listFilesRecursively(path), HttpStatus.OK);
    }
}

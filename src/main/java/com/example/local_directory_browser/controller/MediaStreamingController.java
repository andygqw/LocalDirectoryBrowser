package com.example.local_directory_browser.controller;

import com.example.local_directory_browser.service.MediaStreamer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRange;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.File;
import java.util.List;

@RestController
@RequestMapping("/media")
@CrossOrigin(origins = "*")
public class MediaStreamingController {

    private final MediaStreamer mediaStreamingService;

    @Autowired
    public MediaStreamingController(MediaStreamer mediaStreamingService) {
        this.mediaStreamingService = mediaStreamingService;
    }

    @GetMapping("/video")
    public ResponseEntity<byte[]> streamVideo(@RequestParam String filename,
                                                             @RequestHeader(value = HttpHeaders.RANGE, required = false) String rangeHeader) {

        return mediaStreamingService.streamMedia(filename, getFileExtension(filename), rangeHeader);
    }

    @GetMapping("/audio")
    public ResponseEntity<byte[]> streamAudio(@RequestParam String filename,
                                                             @RequestHeader(value = HttpHeaders.RANGE, required = false) String ranges) {
        return mediaStreamingService.streamMedia(filename, "audio/mpeg", ranges);
    }

    @GetMapping("/image")
    public ResponseEntity<byte[]> streamImage(@RequestParam String filename) {
        return mediaStreamingService.streamMedia(filename, "image/jpeg", null);
    }

    @GetMapping("/pdf")
    public ResponseEntity<byte[]> streamPdf(@RequestParam String filename) {
        return mediaStreamingService.streamMedia(filename, "application/pdf", null);
    }

    private static String getFileExtension(String filePath) {
        File file = new File(filePath);

        String fileName = file.getName();

        int dotIndex = fileName.lastIndexOf('.');

        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            return fileName.substring(dotIndex + 1);
        } else {
            return "";
        }
    }
}

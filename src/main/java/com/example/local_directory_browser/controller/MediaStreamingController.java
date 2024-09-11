package com.example.local_directory_browser.controller;

import com.example.local_directory_browser.service.MediaStreamer;
import com.example.local_directory_browser.service.PdfStreamingService;
import com.example.local_directory_browser.service.StrategyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRange;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.File;

@RestController
@RequestMapping("/media")
@CrossOrigin(origins = "*")
public class MediaStreamingController {

    private final StrategyFactory factory;

    @Autowired
    public MediaStreamingController(StrategyFactory factory) {
        this.factory = factory;
    }

    @GetMapping("/video")
    public ResponseEntity<StreamingResponseBody> streamVideo(@RequestParam String filename,
                                                             @RequestHeader(value = HttpHeaders.RANGE, required = false) String rangeHeader) {

        MediaStreamer mediaStreamer = factory.getStrategy("MediaStreamingService");
        return mediaStreamer.streamMedia(filename, getFileExtension(filename), rangeHeader);
    }

    @GetMapping("/audio")
    public ResponseEntity<StreamingResponseBody> streamAudio(@RequestParam String filename,
                                                             @RequestHeader(value = HttpHeaders.RANGE, required = false) String ranges) {
        MediaStreamer mediaStreamer = factory.getStrategy("MediaStreamingService");
        return mediaStreamer.streamMedia(filename, "audio/mpeg", ranges);
    }

    @GetMapping("/image")
    public ResponseEntity<StreamingResponseBody> streamImage(@RequestParam String filename) {
        MediaStreamer mediaStreamer = factory.getStrategy("MediaStreamingService");
        return mediaStreamer.streamMedia(filename, "image/jpeg", null);
    }

    @GetMapping("/pdf")
    public ResponseEntity<StreamingResponseBody> streamPdf(@RequestParam String filename) {
        MediaStreamer mediaStreamer = factory.getStrategy("PdfStreamingService");
        return mediaStreamer.streamMedia(filename, "application/pdf", null);
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

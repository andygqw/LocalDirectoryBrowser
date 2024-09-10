package com.example.local_directory_browser.controller;

import com.example.local_directory_browser.service.MediaStreamer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRange;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

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
    public ResponseEntity<StreamingResponseBody> streamVideo(@RequestParam String filename,
                                                             @RequestHeader(value = HttpHeaders.RANGE, required = false) List<HttpRange> ranges) {
        return mediaStreamingService.streamMedia(filename, "video/mp4", ranges);
    }

    @GetMapping("/audio")
    public ResponseEntity<StreamingResponseBody> streamAudio(@RequestParam String filename,
                                                             @RequestHeader(value = HttpHeaders.RANGE, required = false) List<HttpRange> ranges) {
        return mediaStreamingService.streamMedia(filename, "audio/mpeg", ranges);
    }

    @GetMapping("/image")
    public ResponseEntity<StreamingResponseBody> streamImage(@RequestParam String filename) {
        return mediaStreamingService.streamMedia(filename, "image/jpeg", null);
    }

    @GetMapping("/pdf")
    public ResponseEntity<StreamingResponseBody> streamPdf(@RequestParam String filename) {
        return mediaStreamingService.streamMedia(filename, "application/pdf", null);
    }
}

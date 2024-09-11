package com.example.local_directory_browser.service;

import org.springframework.http.HttpRange;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.util.List;

public interface MediaStreamer {

    public ResponseEntity<byte[]> streamMedia(String filepath, String mediaType, String ranges);
}

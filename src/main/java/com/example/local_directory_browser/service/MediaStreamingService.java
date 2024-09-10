package com.example.local_directory_browser.service;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRange;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.InputStream;
import java.util.List;

@Service
public class MediaStreamingService implements MediaStreamer{

    private final ResourceLoader resourceLoader;

    public MediaStreamingService(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public ResponseEntity<StreamingResponseBody> streamMedia(String filepath, String mediaType, List<HttpRange> ranges) {
        try {

            Resource resource = resourceLoader.getResource("file:" + filepath);

            InputStream inputStream = resource.getInputStream();
            long contentLength = resource.contentLength();

            StreamingResponseBody stream = outputStream -> {
                byte[] buffer = new byte[2048];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            };

            // If there are range requests (for progressive streaming)
            if (ranges != null && !ranges.isEmpty()) {
                HttpRange range = ranges.get(0);
                long start = range.getRangeStart(contentLength);
                long end = range.getRangeEnd(contentLength);

                return ResponseEntity
                        .status(206) // Partial Content for streaming
                        .header(HttpHeaders.CONTENT_RANGE, "bytes " + start + "-" + end + "/" + contentLength)
                        .header(HttpHeaders.ACCEPT_RANGES, "bytes")
                        .header(HttpHeaders.CONTENT_TYPE, mediaType)
                        .contentLength(end - start + 1)
                        .body(stream);
            }

            return ResponseEntity
                    .ok()
                    .header(HttpHeaders.CONTENT_TYPE, mediaType)
                    .contentLength(contentLength)
                    .body(stream);

        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}

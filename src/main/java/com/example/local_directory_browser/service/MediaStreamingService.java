package com.example.local_directory_browser.service;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRange;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.*;
import java.util.List;

@Service
public class MediaStreamingService implements MediaStreamer{

    private final ResourceLoader resourceLoader;
    private final static long CHUNK_SIZE = 314700;
    private static final String VIDEO_CONTENT = "video/";

    public MediaStreamingService(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    private byte[] readByteRange(String filename, long start, long end) throws IOException {
        try (RandomAccessFile file = new RandomAccessFile(filename, "r")) {
            file.seek(start);  // Move to the start position
            byte[] data = new byte[(int) (end - start + 1)];  // Read only the requested range
            file.readFully(data);
            return data;
        }
    }


    @Override
    public ResponseEntity<byte[]> streamMedia(String filepath, String mediaType, String range) {
        try {
            File file = new File(filepath);
            if (!file.exists()) {
                throw new FileNotFoundException(filepath);
            }

            final long fileSize = file.length();
            long rangeStart = 0;
            long rangeEnd = CHUNK_SIZE;

            if (range == null) {
                rangeEnd = Math.min(CHUNK_SIZE, fileSize - 1);  // Adjust range end to file size
            } else {
                String[] ranges = range.split("-");
                rangeStart = Long.parseLong(ranges[0].substring(6));  // Start of the range
                if (ranges.length > 1) {
                    rangeEnd = Long.parseLong(ranges[1]);
                } else {
                    rangeEnd = rangeStart + CHUNK_SIZE;
                }
                rangeEnd = Math.min(rangeEnd, fileSize - 1);  // Adjust range end to file size
            }

            final byte[] data = readByteRange(filepath, rangeStart, rangeEnd);
            final String contentLength = String.valueOf((rangeEnd - rangeStart) + 1);
            HttpStatus httpStatus = HttpStatus.PARTIAL_CONTENT;

            // Adjust status if this is the last chunk
            if (rangeEnd >= fileSize - 1) {
                httpStatus = HttpStatus.OK;
            }

            return ResponseEntity.status(httpStatus)
                    .header(HttpHeaders.CONTENT_TYPE, VIDEO_CONTENT + mediaType)
                    .header(HttpHeaders.ACCEPT_RANGES, "bytes")
                    .header(HttpHeaders.CONTENT_LENGTH, contentLength)
                    .header(HttpHeaders.CONTENT_RANGE, "bytes " + rangeStart + "-" + rangeEnd + "/" + fileSize)
                    .body(data);

        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}

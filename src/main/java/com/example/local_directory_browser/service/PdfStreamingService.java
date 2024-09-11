package com.example.local_directory_browser.service;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service("PdfStreamingService")
public class PdfStreamingService implements MediaStreamer{

    private static final String PDF_CONTENT_TYPE = "application/pdf";

    public PdfStreamingService() {}

    private StreamingResponseBody createStreamingResponseBody(String filePathString, Long start, Long end) {
        return outputStream -> {
            try (RandomAccessFile file = new RandomAccessFile(filePathString, "r")) {
                byte[] buffer = new byte[1024];
                long position = start;
                file.seek(position);

                while (position < end) {
                    int bytesRead = file.read(buffer);
                    outputStream.write(buffer, 0, bytesRead);
                    position += bytesRead;
                }
                outputStream.flush();
            } catch (IOException e) {
                throw new RuntimeException("Error streaming PDF file", e);
            }
        };
    }

    @Override
    public ResponseEntity<StreamingResponseBody> streamMedia(String filePathString, String mediaType, String rangeHeader) {

        Path filePath = Paths.get(filePathString);

        try {

            Long fileSize = Files.size(filePath);
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.add("Content-Type", PDF_CONTENT_TYPE);
            responseHeaders.add("Content-Length", fileSize.toString());

            StreamingResponseBody responseStream = createStreamingResponseBody(filePathString, 0L, fileSize);

            return new ResponseEntity<>(responseStream, responseHeaders, HttpStatus.OK);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}

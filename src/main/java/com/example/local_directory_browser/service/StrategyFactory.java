package com.example.local_directory_browser.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.util.Map;

@Component
public class StrategyFactory {

    private final Map<String, MediaStreamer> mediaStrategies;

    @Autowired
    public StrategyFactory(Map<String, MediaStreamer> mediaStrategies) {
        this.mediaStrategies = mediaStrategies;
    }

    public MediaStreamer getStrategy(String mediaType) {
        return mediaStrategies.get(mediaType);
    }
}

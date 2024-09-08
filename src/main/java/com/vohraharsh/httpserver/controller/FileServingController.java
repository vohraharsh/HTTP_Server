package com.vohraharsh.httpserver.controller;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/files")
public class FileServingController {

    private final ResourceLoader resourceLoader;
    private static final Map<String, MediaType> EXTENSION_TO_MEDIA_TYPE = new HashMap<>();

    static {
        EXTENSION_TO_MEDIA_TYPE.put("txt", MediaType.TEXT_PLAIN);
        EXTENSION_TO_MEDIA_TYPE.put("jpg", MediaType.IMAGE_JPEG);
        EXTENSION_TO_MEDIA_TYPE.put("png", MediaType.IMAGE_PNG);
        EXTENSION_TO_MEDIA_TYPE.put("pdf", MediaType.APPLICATION_PDF);
        // Add more mappings as needed
    }

    public FileServingController(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @GetMapping("/{filename}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Resource resource = resourceLoader.getResource("classpath:static/files/" + filename);

        if (!resource.exists()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        // Determine MIME type
        String contentType = "application/octet-stream";
        String[] parts = filename.split("\\.");
        if (parts.length > 1) {
            String extension = parts[parts.length - 1].toLowerCase();
            MediaType mediaType = EXTENSION_TO_MEDIA_TYPE.get(extension);
            if (mediaType != null) {
                contentType = mediaType.toString();
            }
        }

        try {
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, contentType)
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
package com.dxerp.ebs.controller;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;

@RestController
@RequestMapping("/api/files")
public class FileController {

    @GetMapping("/users/{filename:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        try {
            // Resolve the file path from the "uploads/users" folder
            Path filePath = Paths.get("uploads").resolve("users").resolve(filename).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (!resource.exists()) {
                return ResponseEntity.notFound().build(); // Return 404 if file doesn't exist
            }

            // Set the correct media type for the file
            MediaType mediaType = MediaType.IMAGE_PNG; // Default to PNG
            if (filename.endsWith(".jpg") || filename.endsWith(".jpeg")) {
                mediaType = MediaType.IMAGE_JPEG;
            } else if (filename.endsWith(".gif")) {
                mediaType = MediaType.IMAGE_GIF;
            } else if (filename.endsWith(".bmp")) {
                mediaType = MediaType.IMAGE_GIF;
            }

            return ResponseEntity.ok()
                    .contentType(mediaType)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename + "\"")
                    .body(resource);

        } catch (IOException e) {
            return ResponseEntity.notFound().build(); // Return 404 on error
        }
    }
}

package com.example.watch.Controller;


import com.example.watch.Entity.Content;
import com.example.watch.Service.Interface.ContentServiceI;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contents")
public class ContentController {

    private final ContentServiceI contentService;

    public ContentController(ContentServiceI contentService) {
        this.contentService = contentService;
    }

    /**
     * Tüm içerikleri getirir.
     * @return İçerik listesi
     */
    @GetMapping("/list")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<List<Content>> getAllContents() {
        List<Content> contents = contentService.getAllContents();
        return ResponseEntity.ok(contents);
    }

    /**
     * Belirli bir türdeki içerikleri getirir.
     * @param type İçerik türü
     * @return İçerik listesi
     */
    @GetMapping("/byType")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<List<Content>> getContentsByType(@RequestParam("type") String type) {
        if (type == null || type.isEmpty()) {
            throw new IllegalArgumentException("Type parameter is required");
        }
        List<Content> contents = contentService.getContentsByType(type);
        return ResponseEntity.ok(contents);
    }

    /**
     * Belirli bir türdeki içerikleri getirir.
     * @param genre İçerik türü
     * @return İçerik listesi
     */
    @GetMapping("/byGenre")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<List<Content>> getContentsByGenre(@RequestParam("genre") String genre) {
        if (genre == null || genre.isEmpty()) {
            throw new IllegalArgumentException("Genre parameter is required");
        }
        List<Content> contents = contentService.getContentsByGenre(genre);
        return ResponseEntity.ok(contents);
    }

}
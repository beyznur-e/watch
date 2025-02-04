package com.example.watch.Controller;
import com.example.watch.Entity.UserContent;
import com.example.watch.Repository.ContentRepository;
import com.example.watch.Repository.UserContentRepository;
import com.example.watch.Repository.UserRepository;
import com.example.watch.Service.Interface.UserContentServiceI;
import com.example.watch.Service.UserContentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-content")
public class UserContentController {

    private final UserContentServiceI userContentService;
    private final UserRepository userRepository;
    private final ContentRepository contentRepository;
    private final UserContentRepository userContentRepository;

    public UserContentController(UserContentServiceI userContentService, UserRepository userRepository, ContentRepository contentRepository, UserContentRepository userContentRepository) {
        this.userContentService = userContentService;
        this.userRepository = userRepository;
        this.contentRepository = contentRepository;
        this.userContentRepository = userContentRepository;
    }

    // İçeriği kullanıcıya kaydetmek için
    @PostMapping("/save")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public void saveContentForUser(@RequestParam("userId") Long userId, @RequestParam("contentId") Long contentId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        contentRepository.findById(contentId)
                .orElseThrow(() -> new RuntimeException("Content not found"));
        userContentService.saveContentForUser(userId, contentId);
    }

    // İçeriği kullanıcıdan çıkarmak için
    @DeleteMapping("/delete")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<String> removeContentFromWatchList(@RequestParam("userId") Long userId, @RequestParam("contentId") Long contentId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        contentRepository.findById(contentId)
                .orElseThrow(() -> new RuntimeException("Content not found"));
        userContentService.removeContentFromWatchList(userId, contentId);
        return ResponseEntity.ok("Content removed from watchlist");
    }


    }




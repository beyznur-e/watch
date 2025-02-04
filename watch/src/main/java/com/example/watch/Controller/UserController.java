package com.example.watch.Controller;
import com.example.watch.DTO.UserDto;
import com.example.watch.Entity.UserContent;
import com.example.watch.Repository.ContentRepository;
import com.example.watch.Repository.UserRepository;
import com.example.watch.Service.Interface.UserServiceI;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserServiceI userService;
    private final UserRepository userRepository;
    private final ContentRepository contentRepository;

    public UserController(UserServiceI userService, UserRepository userRepository, ContentRepository contentRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.contentRepository = contentRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserDto userDto) {
        userService.registerUser(userDto);
        return ResponseEntity.ok("User registered successfully");
    }

    @PutMapping("/update")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<String> updateUser(@RequestParam("userId") Long userId, @RequestBody UserDto userDto) {
        userService.updateUser(userId, userDto);
        return ResponseEntity.ok("User updated successfully");
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> deleteUser(@RequestParam("userId") Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok("User deleted successfully");
    }

    @PostMapping("/addwatchlist")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<String> addContentToWatchList(@RequestParam("userId") Long userId, @RequestParam("contentId") Long contentId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        contentRepository.findById(contentId)
                .orElseThrow(() -> new RuntimeException("Content not found"));
        userService.addContentToWatchList(userId, contentId);
        return ResponseEntity.ok("Content added to watchlist");
    }

    @GetMapping("/watchlist")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<List<UserContent>> getWatchList(@RequestParam("userId") Long userId) {
        List<UserContent> watchList = userService.getWatchList(userId);
        return ResponseEntity.ok(watchList);
    }
}

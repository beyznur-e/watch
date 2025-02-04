package com.example.watch.Controller;

import com.example.watch.DTO.ContentDto;
import com.example.watch.Entity.User;
import com.example.watch.Service.Interface.AdminServiceI;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminServiceI adminService;

    public AdminController(AdminServiceI adminService) {
        this.adminService = adminService;
    }

    //İçerik ekleme
    @PostMapping("/content/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> addContent(@RequestBody ContentDto contentDto) {
        adminService.addContent(contentDto);
        return ResponseEntity.ok("Content added successfully");
    }

    //İçerik güncelleme
    @PutMapping("/content/update")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> updateContent(@RequestParam Long contentId, @RequestBody ContentDto contentDto) {
        adminService.updateContent(contentId, contentDto); // contentId'yi request parametresinden alıyoruz
        return ResponseEntity.ok("Content updated successfully");
    }

    //İçerik silme
    @DeleteMapping("/content/delete")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> deleteContent(@RequestParam Long contentId) {
        adminService.deleteContent(contentId);
        return ResponseEntity.ok("Content deleted successfully");
    }

    //Kullanıcı listeleme
    @GetMapping("/user/list")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<List<User>> listUsers(@RequestParam Long userId) {
        List<User> users = adminService.listUsers(userId); // userId'yi request parametresinden alıyoruz
        return ResponseEntity.ok(users);
    }

    //Kullanıcı silme
    @DeleteMapping("/user/delete")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> deleteUser(@RequestParam Long userId) {
        adminService.deleteUser(userId);
        return ResponseEntity.ok("User deleted successfully");
    }
}


package com.example.watch.Service;

import com.example.watch.DTO.ContentDto;
import com.example.watch.Entity.Content;
import com.example.watch.Entity.Role;
import com.example.watch.Entity.User;
import com.example.watch.Repository.ContentRepository;
import com.example.watch.Repository.UserRepository;
import com.example.watch.Service.Interface.AdminServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class AdminService implements AdminServiceI {

    private final ContentRepository contentRepository;
    private final UserRepository userRepository;

    @Autowired
    public AdminService(ContentRepository contentRepository, UserRepository userRepository) {
        this.contentRepository = contentRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void addContent(ContentDto contentDto) {
            Content content = new Content();
            content.setTitle(contentDto.getTitle());
            content.setGenre(contentDto.getGenre());
            content.setType(contentDto.getType());
            contentRepository.save(content);
    }

    @Override
    public void updateContent(Long content_id, ContentDto contentDto) {
         Content content = contentRepository.findById(content_id)
                    .orElseThrow(() -> new RuntimeException("Content not found"));
            content.setTitle(contentDto.getTitle());
            content.setGenre(contentDto.getGenre());
            content.setType(contentDto.getType());
            contentRepository.save(content);

    }

    @Override
    public void deleteContent(Long contentId) {
        contentRepository.deleteById(contentId);
    }

    @Override
    public List<User> listUsers(Long user_id) {
        if (user_id != null) {
            return userRepository.findById(user_id)
                    .map(Collections::singletonList) // Tek kullanıcıyı liste olarak dön
                    .orElse(Collections.emptyList()); // Kullanıcı yoksa boş liste dön
        }
        return userRepository.findAll(); // ID yoksa tüm kullanıcıları getir
    }


    @Override
    public void deleteUser(Long user_id) {
            userRepository.deleteById(user_id);
   }
}

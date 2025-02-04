package com.example.watch.Service.Interface;

import com.example.watch.DTO.ContentDto;
import com.example.watch.Entity.User;

import java.util.List;

public interface AdminServiceI {
    void addContent(ContentDto contentDto);
    void updateContent(Long content_id, ContentDto contentDto);
    void deleteContent(Long contentId);
    List<User> listUsers(Long user_id);
    void deleteUser(Long user_id);
}

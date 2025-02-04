package com.example.watch.Service.Interface;

import com.example.watch.Entity.Content;
import com.example.watch.Entity.UserContent;

import java.util.List;

public interface UserContentServiceI {
    void saveContentForUser(Long user_id, Long content_id);
    void removeContentFromWatchList(Long user_id, Long content_id);
    List<UserContent> findByUserId(Long userId);
}

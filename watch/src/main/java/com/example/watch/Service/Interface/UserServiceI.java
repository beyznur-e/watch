package com.example.watch.Service.Interface;

import com.example.watch.DTO.UserDto;
import com.example.watch.Entity.UserContent;

import java.util.List;

public interface UserServiceI {
    void registerUser(UserDto userDto);
    void updateUser(Long user_id, UserDto userDto);
    void deleteUser(Long user_id);
    void addContentToWatchList(Long user_id, Long content_id);
    List<UserContent> getWatchList(Long user_id);
}

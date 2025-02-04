package com.example.watch.Service;

import com.example.watch.DTO.UserDto;
import com.example.watch.Entity.User;
import com.example.watch.Entity.UserContent;
import com.example.watch.Repository.ContentRepository;
import com.example.watch.Repository.UserRepository;
import com.example.watch.Service.Interface.UserContentServiceI;
import com.example.watch.Service.Interface.UserServiceI;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.watch.Entity.Role;
import java.util.List;

@Service
public class UserService implements UserServiceI {

    private final UserRepository userRepository;
    private final ContentRepository contentRepository;
    private final UserContentServiceI userContentServiceI;
    private final PasswordEncoder passwordEncoder;


    public UserService(UserRepository userRepository, ContentRepository contentRepository, UserContentServiceI userContentServiceI, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.contentRepository = contentRepository;
        this.userContentServiceI = userContentServiceI;
        this.passwordEncoder = passwordEncoder;
    }

    @Override //Kullanıcı kayıt
    public void registerUser(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword())); // Şifreyi hashle
        user.setRole(userDto.getRole());
        userRepository.save(user);
    }


    @Override //Kullanıcı güncelleme
    public void updateUser(Long user_id, UserDto userDto) {
        User user = userRepository.findById(user_id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword())); // Şifreyi hashle
        userRepository.save(user);
    }

    @Override //Kullanıcı silme
    public void deleteUser(Long user_id) {
        userRepository.deleteById(user_id);
    }

    @Override //İçeriği kullanıcının izleme listesine ekleme
    public void addContentToWatchList(Long user_id, Long content_id) {
        userRepository.findById(user_id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        contentRepository.findById(content_id)
                .orElseThrow(() -> new RuntimeException("Content not found"));

        userContentServiceI.saveContentForUser(user_id, content_id);
    }

    @Override //Kullanıcının izleme listesini alma
    public List<UserContent> getWatchList(Long user_id) {
        User user = userRepository.findById(user_id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return userContentServiceI.findByUserId(user_id);
    }
}

package com.example.watch.DTO;

public class UserContentDto {
    private Long user_id; // Kullanıcı ID'si
    private Long content_id; // İçerik ID'si

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Long getContent_id() {
        return content_id;
    }

    public void setContent_id(Long content_id) {
        this.content_id = content_id;
    }
}

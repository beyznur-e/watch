package com.example.watch.DTO;
import com.example.watch.Entity.Content;
import com.example.watch.Entity.Role;
import java.util.List;

public class UserDto {
    private String username; // Kullanıcı adı
    private String password; // Parola
    private Role role; // Kullanıcı rolü

    public UserDto() {
    }

    public UserDto( String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

}

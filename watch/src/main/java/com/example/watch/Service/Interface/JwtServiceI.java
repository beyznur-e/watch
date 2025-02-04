package com.example.watch.Service.Interface;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtServiceI {
    String extractUsername(String token);
    boolean validateToken(String token, UserDetails userDetails);
    String generateToken(UserDetails userDetails);
}
package com.example.watch.Controller;

import com.example.watch.Entity.Role;
import com.example.watch.Entity.User;
import com.example.watch.Repository.UserRepository;
import com.example.watch.Service.CustomUserDetailsService;
import com.example.watch.Service.Interface.JwtServiceI;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomUserDetailsService userDetailsService;
    private final JwtServiceI jwtServiceI;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder,
                          CustomUserDetailsService userDetailsService, JwtServiceI jwtServiceI) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
        this.jwtServiceI = jwtServiceI;
    }

    @PostMapping("/register")
    public String register(@RequestBody User user) {
        try {
            // Şifreyi encode et
            user.setPassword(passwordEncoder.encode(user.getPassword()));

            // Eğer rol boşsa, varsayılan olarak USER ata
            if (user.getRole() == null) {
                user.setRole(Role.USER);  // Varsayılan role atanıyor
            }

            // Kullanıcıyı kaydediyoruz
            userRepository.save(user);
            return "User registered successfully!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Registration failed: " + e.getMessage();
        }
    }

    @PostMapping("/login")
    public String login(@RequestBody User user) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
        if (!passwordEncoder.matches(user.getPassword(), userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid username or password");
        }
        return jwtServiceI.generateToken(userDetails);
    }
}
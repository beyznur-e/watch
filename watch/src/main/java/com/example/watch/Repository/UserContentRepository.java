package com.example.watch.Repository;

import com.example.watch.Entity.UserContent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserContentRepository extends JpaRepository<UserContent, Long> {
    List<UserContent> findByUserId(Long userId);
    Optional<UserContent> findByUserIdAndContentId(Long userId, Long contentId);
}

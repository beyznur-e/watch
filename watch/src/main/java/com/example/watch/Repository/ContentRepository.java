package com.example.watch.Repository;

import com.example.watch.Entity.Content;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContentRepository extends JpaRepository<Content, Long> {
    @Override
    List<Content> findAll();
}

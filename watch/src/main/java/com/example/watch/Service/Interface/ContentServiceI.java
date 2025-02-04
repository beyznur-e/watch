package com.example.watch.Service.Interface;

import com.example.watch.Entity.Content;

import java.util.List;

public interface ContentServiceI {
    List<Content> getAllContents();
    List<Content> getContentsByType(String type);
    List<Content> getContentsByGenre(String genre);
}

package com.example.watch.Service;

import com.example.watch.Entity.Content;
import com.example.watch.Repository.ContentRepository;
import com.example.watch.Service.Interface.ContentServiceI;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContentService implements ContentServiceI {

    private final ContentRepository contentRepository;

    public ContentService(ContentRepository contentRepository) {
        this.contentRepository = contentRepository;
    }

    @Override
    public List<Content> getAllContents() {
        return contentRepository.findAll();
    }

    @Override
    public List<Content> getContentsByType(String type) {
        return contentRepository.findAll().stream()
                .filter(content -> content.getType().equalsIgnoreCase(type)).collect(Collectors.toList());
    }

    @Override
    public List<Content> getContentsByGenre(String genre) {
        return contentRepository.findAll().stream()
                .filter(content -> content.getGenre().equalsIgnoreCase(genre)).collect(Collectors.toList());
    }

}

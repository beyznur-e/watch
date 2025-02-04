package com.example.watch.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "watch_content")
public class Content {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "genre")
    private String genre;

    @Column(name = "type")
    private String type;

    @ManyToMany(mappedBy = "savedContents")
    @JsonIgnore
    private List<User> savedByUsers;

    public Content() {
    }

    public Content(Long id, String title, String genre, String type, List<User> savedByUsers) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.type = type;
        this.savedByUsers = savedByUsers;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<User> getSavedByUsers() {
        return savedByUsers;
    }

    public void setSavedByUsers(List<User> savedByUsers) {
        this.savedByUsers = savedByUsers;
    }
}

package com.example.watch.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "watch_user_content")
public class UserContent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "content_id")
    private Long content_id;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    @JsonIgnore  // Bu anotasyon, JSON serileştirme sırasında User bilgisini gizler
    private User user;

    @ManyToOne
    @JoinColumn(name = "content_id", insertable = false, updatable = false)
    @JsonIgnore  // Bu anotasyon, JSON serileştirme sırasında User bilgisini gizler
    private Content content;

    public UserContent() {
    }

    public UserContent(Long id, Long userId, Long content_id, User user, Content content) {
        this.id = id;
        this.userId = userId;
        this.content_id = content_id;
        this.user = user;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getContent_id() {
        return content_id;
    }

    public void setContent_id(Long content_id) {
        this.content_id = content_id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }
}

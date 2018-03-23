package com.kyrutech.kyruchat.models;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Post {
    @GeneratedValue
    @Id
    int id;

    @Column(nullable = false)
    String username;

    @Column(nullable = false)
    String content;

    @Column(nullable = false)
    LocalDateTime time;

    @OneToMany
    Post parent;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public Post getParent() {
        return parent;
    }

    public void setParent(Post parent) {
        this.parent = parent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Post post = (Post) o;

        if (id != post.id) return false;
        if (!username.equals(post.username)) return false;
        if (!content.equals(post.content)) return false;
        if (!time.equals(post.time)) return false;
        return parent != null ? parent.equals(post.parent) : post.parent == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + username.hashCode();
        result = 31 * result + content.hashCode();
        result = 31 * result + time.hashCode();
        result = 31 * result + (parent != null ? parent.hashCode() : 0);
        return result;
    }
}

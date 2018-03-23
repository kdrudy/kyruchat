package com.kyrutech.kyruchat.repositories;

import com.kyrutech.kyruchat.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findByParent(Post parent);
    List<Post> findByUser(String user);
}

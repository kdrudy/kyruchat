package com.kyrutech.kyruchat.repositories;

import com.kyrutech.kyruchat.models.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findByParent(Post parent);
    List<Post> findByParent(Post parent, Sort sort);
    List<Post> findByParent(Post parent, Pageable page);

    List<Post> findByUsername(String user);
    List<Post> findByUsername(String user, Sort sort);
    List<Post> findByUsername(String user, Pageable page);

    List<Post> findByParentIsNull();
    List<Post> findByParentIsNull(Sort sort);
    List<Post> findByParentIsNull(Pageable page);
}

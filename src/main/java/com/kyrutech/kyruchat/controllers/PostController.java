package com.kyrutech.kyruchat.controllers;

import com.kyrutech.kyruchat.models.Post;
import com.kyrutech.kyruchat.repositories.PostRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.List;

@RestController
public class PostController {

    @Inject
    PostRepository posts;

    @RequestMapping(value = "/posts", method = RequestMethod.GET)
    public List<Post> getPosts(Integer page, Integer size) {
        if(page != null && size != null) {
            return posts.findByParentIsNull(PageRequest.of(page, size, new Sort(Sort.Direction.DESC, "time")));
        } else {
            return posts.findByParentIsNull(PageRequest.of(0, 10, new Sort(Sort.Direction.DESC, "time")));
        }
    }

    @RequestMapping(value = "/pagecount", method = RequestMethod.GET)
    public Long getNumberOfPosts(Integer size) {
        long postCount = posts.count();
        long pageCount = postCount/size;
        if(postCount%size != 0) {
            pageCount++;
        }
        return pageCount;
    }

    @RequestMapping(value = "/post", method = RequestMethod.POST)
    public void addPost(@RequestBody Post post) {
        post.setTime(LocalDateTime.now());
        posts.save(post);
    }
}

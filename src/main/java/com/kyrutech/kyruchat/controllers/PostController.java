package com.kyrutech.kyruchat.controllers;

import com.kyrutech.kyruchat.models.Post;
import com.kyrutech.kyruchat.repositories.PostRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
public class PostController {

    @Inject
    PostRepository posts;

    @RequestMapping(value = "/posts", method = RequestMethod.GET)
    public List<Post> getPosts(Integer page, Integer size) {
        List<Post> postList = new ArrayList<>();
        if(page != null && size != null) {
            postList = posts.findByParentIsNull(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "time")));
        } else {
            postList = posts.findByParentIsNull(PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "time")));
        }

        for(Post p : postList) {
            p.setReplyCount(posts.countByParent(p));
        }

        return postList;
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

    @RequestMapping(value = "/post", method = RequestMethod.GET)
    public Post getPost(Integer id) {

        Post p = posts.findById(id).orElse(null);
        if(p!=null) {
            p.setReplyCount(posts.countByParent(p));
        }
        return p;
    }

    @RequestMapping(value = "/replies", method = RequestMethod.GET)
    public List<Post> getReplies(Integer id) {
        Post post = posts.findById(id).orElse(null);
        List<Post> replyList =  posts.findByParent(post, Sort.by(Sort.Direction.DESC, "time"));

        for(Post p : replyList) {
            p.setReplyCount(posts.countByParent(p));
        }

        return replyList;
    }
}

package com.kyrutech.kyruchat.controllers;

import com.kyrutech.kyruchat.repositories.PostRepository;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@RestController
public class PostController {

    @Inject
    PostRepository posts;


}

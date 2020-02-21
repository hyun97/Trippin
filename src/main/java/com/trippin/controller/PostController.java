package com.trippin.controller;

import com.trippin.controller.dto.PostDto;
import com.trippin.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/post")
public class PostController {

    private final PostService postService;

    // Create
    @PostMapping
    public void createPost(@RequestBody PostDto postDto) {
        postService.createPost(postDto);
    }

    // Read
    @GetMapping("/page/{pageId}")
    public List<PostDto> getPagePost(@PathVariable int pageId) {
        return postService.getPagePost(pageId);
    }

    // Update
    @PutMapping("/{id}")
    public void updatePost(@PathVariable Long id, @RequestBody PostDto postDto) {
        postService.updatePost(id, postDto);
    }

    // Delete
    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable Long id) {
        postService.deletePost(id);
    }

}

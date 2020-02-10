package com.trippin.controller;

import com.trippin.controller.dto.PostDto;
import com.trippin.service.PostService;
import lombok.RequiredArgsConstructor;
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

    // create
    @PostMapping
    public void createPost(@RequestBody PostDto postDto) {
        postService.createPost(postDto);
    }

    // read
    @GetMapping
    public List<PostDto> getPost() {
        return postService.getPost();
    }

    // update
    @PutMapping("/{id}")
    public void updatePost(@PathVariable Long id, @RequestBody PostDto postDto) {
        postService.updatePost(id, postDto);
    }

    // delete
    // TODO: 작성글과 작성글->댓글 까지 모두 삭제
    // Join 된 댓글까지 모두 지우는 방법

}

package com.trippin.controller;

import com.trippin.config.auth.LoginUser;
import com.trippin.config.auth.SessionUser;
import com.trippin.controller.dto.PagePostDto;
import com.trippin.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/post")
public class PostController {

    private final PostService postService;

    // Create
    @PostMapping
    public void createPost(HttpServletResponse httpServletResponse,
                           Long userId,
                           Long countryId,
                           MultipartFile image,
                           String region,
                           String content) throws IOException {
        postService.createPost(userId, countryId, image, region, content);

        httpServletResponse.sendRedirect("/");
    }

    // Read All
    @GetMapping("/page/{pageId}")
    public List<PagePostDto> getPagePost(@PathVariable int pageId, @LoginUser SessionUser user) {
        return postService.getPagePost(pageId, user);
    }

    // Update
    @PutMapping("/{id}")
    public void updatePost(HttpServletResponse httpServletResponse,
                           @PathVariable Long id,
                           MultipartFile image,
                           String region,
                           String content) throws IOException {

        postService.updatePost(id, image, region, content);

        httpServletResponse.sendRedirect("/post/" + id);
    }

    // Delete
    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable Long id) {
        postService.deletePost(id);
    }

}

package com.trippin.controller;

import com.trippin.controller.dto.BookmarkDto;
import com.trippin.service.BookmarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/bookmark")
public class BookmarkController {

    private final BookmarkService bookmarkService;

    // Create
    @PostMapping("/{postId}")
    public void createBookmark(@PathVariable Long postId, @RequestBody BookmarkDto bookmarkDto) {
        bookmarkService.create(postId, bookmarkDto);
    }

}

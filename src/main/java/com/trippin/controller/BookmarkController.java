package com.trippin.controller;

import com.trippin.controller.dto.BookmarkDto;
import com.trippin.service.BookmarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class BookmarkController {

    private final BookmarkService bookmarkService;

    // Create Bookmark
    @PostMapping("/bookmark/{postId}")
    public void createBookmark(@PathVariable Long postId, @RequestBody BookmarkDto bookmarkDto) {
        bookmarkService.createBookmark(postId, bookmarkDto);
    }

    // Create Favorite
    @PostMapping("/favorite/{postId}")
    public void createFavorite(@PathVariable Long postId, @RequestBody BookmarkDto bookmarkDto) {
        bookmarkService.createFavorite(postId, bookmarkDto);
    }

    // Delete Bookmark
    @DeleteMapping("/bookmark/{postId}")
    public void deleteBookmark(@PathVariable Long postId, @RequestBody BookmarkDto bookmarkDto) {
        bookmarkService.deleteBookmark(postId, bookmarkDto);
    }

    // Delete Favorite
    @DeleteMapping("/favorite/{postId}")
    public void deleteFavorite(@PathVariable Long postId, @RequestBody BookmarkDto bookmarkDto) {
        bookmarkService.deleteFavorite(postId, bookmarkDto);
    }

}

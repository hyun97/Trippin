package com.trippin.controller;

import com.trippin.service.FollowingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/following")
public class FollowingController {

    private final FollowingService followingService;

    // Create Following
    @PostMapping("/{userId}")
    public void createFollowing(@PathVariable Long userId) {
        followingService.createFollowing(userId);
    }

    // Delete Following
    @DeleteMapping("/{userId}")
    public void deleteFollowing(@PathVariable Long userId) {
        followingService.deleteFollowing(userId);
    }

}

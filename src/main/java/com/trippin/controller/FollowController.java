package com.trippin.controller;

import com.trippin.controller.dto.FollowDto;
import com.trippin.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/follow")
public class FollowController {

    private final FollowService followService;

    // Create Follow
    @PostMapping("/{userId}")
    public void createFollowing(@PathVariable Long userId, @RequestBody FollowDto followDto) {
        followService.createFollowing(userId, followDto);
    }

    // Delete Follow
    @DeleteMapping("/{userId}")
    public void deleteFollowing(@PathVariable Long userId, @RequestBody FollowDto followDto) {
        followService.deleteFollowing(userId, followDto);
    }

}

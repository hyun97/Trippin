package com.trippin.service;

import com.trippin.domain.Following;
import com.trippin.domain.FollowingRepository;
import com.trippin.domain.User;
import com.trippin.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FollowingService {

    private final FollowingRepository followingRepository;
    private final UserRepository userRepository;

    public void createFollowing(Long userId) {
        User user = userRepository.findById(userId).orElse(null);

        Following following = Following.builder()
                .user(user)
                .build();

        followingRepository.save(following);
    }

    public void deleteFollowing(Long userId) {
        User user = userRepository.findById(userId).orElse(null);

        Following following = followingRepository.findByUserId(user.getId());

        followingRepository.delete(following);
    }

}

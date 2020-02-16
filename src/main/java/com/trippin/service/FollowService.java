package com.trippin.service;

import com.trippin.controller.dto.FollowDto;
import com.trippin.domain.Follow;
import com.trippin.domain.FollowRepository;
import com.trippin.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class FollowService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    public void createFollowing(Long userId, FollowDto followDto) {
        Follow follow = Follow.builder()
                .following(userRepository.getOne(userId))
                .follower(userRepository.getOne(followDto.getUserId()))
                .build();

        followRepository.save(follow);
    }

    public void deleteFollowing(Long userId, FollowDto followDto) {
        followRepository.deleteByFollowerIdAndFollowingId(followDto.getUserId(), userId);
    }

}

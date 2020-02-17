package com.trippin.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    void deleteByFollowerIdAndFollowingId(Long follower, Long following);

    List<Follow> findByFollowerIdAndFollowingId(Long follower, Long following);

    List<Follow> findByFollowerId(Long followerId);

}

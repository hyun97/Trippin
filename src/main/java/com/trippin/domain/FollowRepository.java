package com.trippin.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    void deleteByFollowerIdAndFollowingId(Long follower, Long following);

    List<Follow> findByFollowerIdAndFollowingId(Long follower, Long following);

    List<Follow> findFollowingIdByFollowerId(Long userId);

    List<Follow> findFollowerIdByFollowingId(Long userId);

    List<Follow> findByFollowingId(Long userId);

    Integer countFollowingIdByFollowerId(Long userId);

    Integer countFollowerIdByFollowingId(Long userId);

}

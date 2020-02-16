package com.trippin.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowingRepository extends JpaRepository<Following, Long> {

    Following findByUserId(Long userId);

}

package com.trippin.controller.dto;

import com.trippin.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FollowDto {

    private Long userId;

    public FollowDto(User user) {
        this.userId = user.getId();
    }

}
package com.trippin.controller.dto;

import com.trippin.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserDto {

    private String picture;

    private String name;

    private String feel;

    public UserDto(User user) {
        this.picture = user.getPicture();
        this.name = user.getName();
        this.feel = user.getFeel();
    }
}

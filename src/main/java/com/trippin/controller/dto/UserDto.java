package com.trippin.controller.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserDto {

    private String image;

    private String email;

    private String name;

    private String comment;

}

package com.trippin.controller.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserDto {

    private String email;

    private String name;

}

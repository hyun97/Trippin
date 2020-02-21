package com.trippin.controller.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SearchDto {

    String country;

    String region;

    public SearchDto(String country, String region) {
        this.country = country;
        this.region = region;
    }

}

package com.trippin.controller.dto;

import com.trippin.domain.Country;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CountryDto {

    private String image;

    private String name;

    private String content;

    private Long userId;

    public CountryDto(Country country) {
        this.image = country.getImage();
        this.name = country.getName();
        this.content = country.getContent();
        this.userId = country.getUser().getId();
    }
}

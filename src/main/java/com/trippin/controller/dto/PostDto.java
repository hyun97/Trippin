package com.trippin.controller.dto;

import com.trippin.domain.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostDto {

    private String image;

    private String region;

    private String content;

    private Integer favorite;

    private Long countryId;

    public PostDto(Post post) {
        this.image = post.getImage();
        this.region = post.getRegion();
        this.content = post.getContent();
        this.favorite = post.getFavorite();
        this.countryId = post.getCountry().getId();
    }

}

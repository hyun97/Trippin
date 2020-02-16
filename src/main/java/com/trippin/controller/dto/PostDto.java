package com.trippin.controller.dto;

import com.trippin.domain.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostDto {

    private Long id;

    private String image;

    private String region;

    private String content;

    private Long countryId;

    private Long userId;

    public PostDto(Post post) {
        this.id = post.getId();
        this.image = post.getImage();
        this.region = post.getRegion();
        this.content = post.getContent();
        this.countryId = post.getCountry().getId();
        this.userId = post.getUser().getId();
    }

}

package com.trippin.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PagePostDto {

    private Long postId;

    private String image;

    private String region;

    private String content;

    private String countryName;

    private String authorName;

    private Long userId;

    private boolean isLogin;

    private boolean isBookmark;

    private boolean isFavorite;

    private boolean isFollow;

    private boolean isAuthor;

    private Integer countFavorite;

    private Integer countComment;

}

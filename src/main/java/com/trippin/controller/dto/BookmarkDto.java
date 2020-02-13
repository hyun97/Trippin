package com.trippin.controller.dto;

import com.trippin.domain.Bookmark;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BookmarkDto {

    private Long userId;

    public BookmarkDto(Bookmark bookmark) {
        this.userId = bookmark.getUser().getId();
    }

}
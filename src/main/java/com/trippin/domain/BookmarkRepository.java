package com.trippin.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    List<Bookmark> findPostByUserIdAndPostIdAndSaveIsNotNull(Long userId, Long postId);

    List<Bookmark> findPostByUserIdAndPostIdAndSaveIsNull(Long userId, Long postId);

    List<Bookmark> findPostByUserIdAndSaveIsNotNull(Long userId);

    Integer countBookmarkByUserIdAndSaveIsNotNull(Long userId);

    Integer countBookmarkByPostIdAndSaveIsNull(Long postId);

}

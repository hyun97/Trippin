package com.trippin.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    List<Bookmark> findPostByUserIdAndPostIdAndSaveIsNotNull(Long userId, Long postId);

    List<Bookmark> findPostByUserIdAndPostIdAndSaveIsNull(Long userId, Long postId);

    Page<Bookmark> findPostByUserIdAndSaveIsNotNull(Long userId, Pageable pageable);

    Integer countBookmarkByUserIdAndSaveIsNotNull(Long userId);

    Integer countBookmarkByPostIdAndSaveIsNull(Long postId);

}

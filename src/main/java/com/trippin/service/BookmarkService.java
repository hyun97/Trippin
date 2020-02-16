package com.trippin.service;

import com.trippin.controller.dto.BookmarkDto;
import com.trippin.domain.Bookmark;
import com.trippin.domain.BookmarkRepository;
import com.trippin.domain.Post;
import com.trippin.domain.PostRepository;
import com.trippin.domain.User;
import com.trippin.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    // 북마크 추가
    public void createBookmark(Long postId, BookmarkDto bookmarkDto) {
        User loginUser = userRepository.getOne(bookmarkDto.getUserId());
        Post post = postRepository.findById(postId).orElse(null);

        List<Bookmark> bookmark =
                bookmarkRepository.findPostByUserIdAndPostIdAndSaveIsNotNull(loginUser.getId(), postId);

        if (bookmark.size() == 0) {
            Bookmark newBookmark = Bookmark.builder()
                    .post(post)
                    .save(1)
                    .user(loginUser)
                    .build();

            bookmarkRepository.save(newBookmark);
        }
    }

    // 좋아요 추가
    public void createFavorite(Long postId, BookmarkDto bookmarkDto) {
        User loginUser = userRepository.getOne(bookmarkDto.getUserId());
        Post post = postRepository.findById(postId).orElse(null);

        List<Bookmark> favorite =
                bookmarkRepository.findPostByUserIdAndPostIdAndSaveIsNull(loginUser.getId(), postId);

        if (favorite.size() == 0) {
            Bookmark newBookmark = Bookmark.builder()
                    .post(post)
                    .user(loginUser)
                    .build();

            bookmarkRepository.save(newBookmark);
        }
    }

    // 북마크 삭제
    public void deleteBookmark(Long postId, BookmarkDto bookmarkDto) {
        User loginUser = userRepository.getOne(bookmarkDto.getUserId());

        List<Bookmark> bookmark =
                bookmarkRepository.findPostByUserIdAndPostIdAndSaveIsNotNull(loginUser.getId(), postId);

        bookmarkRepository.delete(bookmark.get(0));
    }

    // 좋아요 삭제
    public void deleteFavorite(Long postId, BookmarkDto bookmarkDto) {
        User loginUser = userRepository.getOne(bookmarkDto.getUserId());

        List<Bookmark> favorite =
                bookmarkRepository.findPostByUserIdAndPostIdAndSaveIsNull(loginUser.getId(), postId);

        bookmarkRepository.delete(favorite.get(0));
    }

}
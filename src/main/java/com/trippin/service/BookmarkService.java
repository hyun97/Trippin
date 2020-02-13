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

@RequiredArgsConstructor
@Transactional
@Service
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public void create(Long postId, BookmarkDto bookmarkDto) {
        Post post = postRepository.findById(postId).orElse(null);
        User user = userRepository.getOne(bookmarkDto.getUserId());

        Bookmark bookmark = Bookmark.builder()
                .post(post)
                .user(user)
                .build();

        bookmarkRepository.save(bookmark);
    }

}

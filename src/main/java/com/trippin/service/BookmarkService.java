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

import java.util.ArrayList;
import java.util.List;

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

        /*
            TODO: REFACTOR
            현재 로그인된 유저가 post 를 가지고 있지 않으면 저장
         */

        // 유저가 가지고 있는 postId 목록
        List<Long> postIdList = new ArrayList<>();

        boolean valid = true;

        // 유저가 가지고 있는 bookmark 가 하나도 없다면 바로 추가
        if (user.getBookmark().size() == 0) {
            Bookmark bookmark = Bookmark.builder()
                    .post(post)
                    .user(user)
                    .build();

            bookmarkRepository.save(bookmark);
        } else {
            // 유저가 가지고 있는 북마크 postId 를 배열에 추가
            for (int i = 0; i < user.getBookmark().size(); i++) {
                postIdList.add(user.getBookmark().get(i).getPost().getId());
            }

            // postId 리스트 중 전달받은 postId 와 중복 되는게 있다면 false 후 종료
            for (int i = 0; i < postIdList.size(); i++) {
                if (postIdList.get(i).equals(postId)) {
                    valid = false;
                    return;
                }
            }

            // false 가 아니라면 추가
            if (valid) {
                Bookmark bookmark = Bookmark.builder()
                        .post(post)
                        .user(user)
                        .build();

                bookmarkRepository.save(bookmark);
            }
        }

    }

}

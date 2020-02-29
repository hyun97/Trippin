package com.trippin.controller.aop;

import com.trippin.config.auth.SessionUser;
import com.trippin.domain.Bookmark;
import com.trippin.domain.BookmarkRepository;
import com.trippin.domain.CommentRepository;
import com.trippin.domain.Follow;
import com.trippin.domain.FollowRepository;
import com.trippin.domain.Post;
import com.trippin.service.PostService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Aspect
@Component
@RequiredArgsConstructor
public class ValidPostAspect {

    private final BookmarkRepository bookmarkRepository;
    private final FollowRepository followRepository;
    private final PostService postService;
    private final CommentRepository commentRepository;

    @Around("@annotation(ValidPost)")
    public Object validPost(ProceedingJoinPoint joinPoint) throws Throwable {

        SessionUser user = Arrays.stream(joinPoint.getArgs())
                .filter(SessionUser.class::isInstance)
                .map(SessionUser.class::cast)
                .findFirst()
                .orElse(null);

        Pageable pageable = Arrays.stream(joinPoint.getArgs())
                .filter(Pageable.class::isInstance)
                .map(Pageable.class::cast)
                .findFirst()
                .orElse(null);

        Page<Post> postList = postService.getPost(pageable);

        if (user != null) {
            postList.forEach(post -> {
                List<Bookmark> bookmark =
                        bookmarkRepository.findPostByUserIdAndPostIdAndSaveIsNotNull(user.getId(), post.getId());
                List<Bookmark> favorite =
                        bookmarkRepository.findPostByUserIdAndPostIdAndSaveIsNull(user.getId(), post.getId());
                List<Follow> follow =
                        followRepository.findByFollowerIdAndFollowingId(user.getId(), post.getUser().getId());

                if (!bookmark.isEmpty()) post.setValidBookmark(true);
                if (!favorite.isEmpty()) post.setValidFavorite(true);
                if (!follow.isEmpty()) post.setValidFollow(true);
                if (post.getUser().getId().equals(user.getId())) post.setValidUser(true);
            });
        }

//        postList.forEach(post -> {
//            post.setFavorite(bookmarkRepository.countBookmarkByPostIdAndSaveIsNull(post.getId()));
//            post.setCountComment(commentRepository.countByPostId(post.getId()));
//        });

        Object proceed = joinPoint.proceed();

        return proceed;
    }

}

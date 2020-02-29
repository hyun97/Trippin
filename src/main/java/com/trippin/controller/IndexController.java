package com.trippin.controller;

import com.trippin.config.auth.LoginUser;
import com.trippin.config.auth.SessionUser;
import com.trippin.controller.aop.GetLoginInfo;
import com.trippin.controller.aop.ValidPost;
import com.trippin.domain.*;
import com.trippin.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final CountryRepository countryRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final BookmarkRepository bookmarkRepository;
    private final FollowRepository followRepository;
    private final CommentRepository commentRepository;
    private final PostService postService;

    // 게시글 상세
    @GetLoginInfo
    @GetMapping("/post/{postId}")
    public String cardDetail(@PathVariable Long postId, Model model, @LoginUser SessionUser user) {
        Post post = postRepository.findById(postId).orElse(null);

        List<Comment> commentList = commentRepository.findByPostIdOrderByCreatedAtDesc(postId);

        if (user != null) {
            List<Bookmark> favorite =
                    bookmarkRepository.findPostByUserIdAndPostIdAndSaveIsNull(user.getId(), postId);
            List<Bookmark> bookmark =
                    bookmarkRepository.findPostByUserIdAndPostIdAndSaveIsNotNull(user.getId(), postId);
            List<Follow> follow =
                    followRepository.findByFollowerIdAndFollowingId(user.getId(), post.getUser().getId());
            if (!favorite.isEmpty()) post.setValidFavorite(true);
            if (!bookmark.isEmpty()) post.setValidBookmark(true);
            if (!follow.isEmpty()) post.setValidFollow(true);
            if (post.getUser().getId().equals(user.getId())) post.setValidUser(true);

            commentList.forEach(comment -> {
                List<Comment> comments =
                        commentRepository.findByIdAndPostIdAndAuthorId(comment.getId(), postId, user.getId());

                if (!comments.isEmpty()) comment.setValidComment(true);
            });
        }

        model.addAttribute("comments", commentList);
        model.addAttribute("post", post);
        model.addAttribute("countFavorite", bookmarkRepository.countBookmarkByPostIdAndSaveIsNull(postId));
        model.addAttribute("countComments", commentRepository.countByPostId(postId));

        return "partial/post/post-detail";
    }

    // 메인
    @GetLoginInfo
    @ValidPost
    @GetMapping("/")
    public String index(@PageableDefault Pageable pageable, Model model, @LoginUser SessionUser user) {
        Page<Post> postList = postService.getPost(pageable);

        postList.forEach(post -> {
            post.setFavorite(bookmarkRepository.countBookmarkByPostIdAndSaveIsNull(post.getId()));
            post.setCountComment(commentRepository.countByPostId(post.getId()));
        });

        model.addAttribute("post", postList);
        model.addAttribute("totalPage", postList.getTotalPages());

        return "index";
    }

    // 검색된 게시물
    @GetLoginInfo
    @ValidPost
    @GetMapping("/search/{search}")
    public String searchPost(@PageableDefault Pageable pageable, @PathVariable String search, Model model,
                             @LoginUser SessionUser user) {
        Page<Post> postList = postService.getSearchPost(search, search, pageable);

        postList.forEach(post -> {
            post.setFavorite(bookmarkRepository.countBookmarkByPostIdAndSaveIsNull(post.getId()));
            post.setCountComment(commentRepository.countByPostId(post.getId()));
        });

        if (postList.isEmpty()) {
            model.addAttribute("validSearch", true);
        }

        model.addAttribute("post", postList);
        model.addAttribute("search", search);
        model.addAttribute("totalPage", postList.getTotalPages() - 1);

        return "search-index";
    }

    // 유저 게시글 출력
    @GetLoginInfo
    @ValidPost
    @GetMapping("/user/{userId}/post")
    public String readUserPost(@PageableDefault Pageable pageable, @PathVariable Long userId, Model model,
                               @LoginUser SessionUser user) {
        Page<Post> postList = postService.getUserPost(userId, pageable);

        postList.forEach(post -> {
            post.setFavorite(bookmarkRepository.countBookmarkByPostIdAndSaveIsNull(post.getId()));
            post.setCountComment(commentRepository.countByPostId(post.getId()));
        });

        model.addAttribute("post", postList);
        model.addAttribute("user", userRepository.findById(userId).orElse(null));
        model.addAttribute("totalPage", postList.getTotalPages() - 1);

        return "user-index";
    }

    // 유저 북마크 게시글 출력
    @GetLoginInfo
    @GetMapping("/user/{userId}/bookmark")
    public String readUserBookmark(@PageableDefault Pageable pageable, @PathVariable Long userId, Model model,
                                   @LoginUser SessionUser loginUser) {
        Page<Bookmark> bookmarkList = postService.getBookmarkPost(userId, pageable);

        if (loginUser != null) {
            bookmarkList.forEach(bookmark -> {
                List<Bookmark> favorite =
                        bookmarkRepository.findPostByUserIdAndPostIdAndSaveIsNull(loginUser.getId(),
                                bookmark.getPost().getId());
                List<Follow> follow =
                        followRepository.findByFollowerIdAndFollowingId(loginUser.getId(),
                                bookmark.getPost().getUser().getId());

                if (!favorite.isEmpty()) bookmark.getPost().setValidFavorite(true);
                if (!follow.isEmpty()) bookmark.getPost().setValidFollow(true);
                if (bookmark.getPost().getUser().getId().equals(loginUser.getId()))
                    bookmark.getPost().setValidUser(true);
            });
        }

        bookmarkList.forEach(p -> {
            p.getPost().setFavorite(bookmarkRepository.countBookmarkByPostIdAndSaveIsNull(p.getPost().getId()));
            p.getPost().setCountComment(commentRepository.countByPostId(p.getPost().getId()));
        });

        model.addAttribute("bookmark", bookmarkList);
        model.addAttribute("totalPage", bookmarkList.getTotalPages() - 1);

        return "bookmark-index";
    }

    // 나라 게시글 출력
    @GetLoginInfo
    @ValidPost
    @GetMapping("/country/{countryId}")
    public String readCountryPost(@PageableDefault Pageable pageable, @PathVariable Long countryId, Model model,
                                  @LoginUser SessionUser user) {
        Page<Post> postList = postService.getCountryPost(countryId, pageable);

        postList.forEach(post -> {
            post.setFavorite(bookmarkRepository.countBookmarkByPostIdAndSaveIsNull(post.getId()));
            post.setCountComment(commentRepository.countByPostId(post.getId()));
        });

        Country country = countryRepository.findById(countryId).orElse(null);

        model.addAttribute("post", postList);
        model.addAttribute("country", country);
        model.addAttribute("totalPage", postList.getTotalPages() - 1);

        return "country-index";
    }

    // 팔로잉 게시글 출력
    @GetLoginInfo
    @GetMapping("/user/{userId}/follow")
    public String readFollowingPost(@PageableDefault(size = 9) Pageable pageable,
                                    @PathVariable Long userId,
                                    Model model,
                                    @LoginUser SessionUser loginUser) {
        List<Follow> followList = followRepository.findFollowingIdByFollowerId(userId);
        List<Post> postList = new ArrayList<>();

        followList.forEach(follow -> {
            postList.addAll(postService.getFollowUserPost(follow.getFollowing().getId()));

            if (loginUser != null) {
                postList.forEach(post -> {
                    List<Bookmark> bookmark =
                            bookmarkRepository.findPostByUserIdAndPostIdAndSaveIsNotNull(loginUser.getId(),
                                    post.getId());
                    List<Bookmark> favorite =
                            bookmarkRepository.findPostByUserIdAndPostIdAndSaveIsNull(loginUser.getId(), post.getId());
                    List<Follow> isFollow =
                            followRepository.findByFollowerIdAndFollowingId(loginUser.getId(), post.getUser().getId());
                    if (!bookmark.isEmpty()) post.setValidBookmark(true);
                    if (!favorite.isEmpty()) post.setValidFavorite(true);
                    if (!isFollow.isEmpty()) post.setValidFollow(true);
                    if (post.getUser().getId().equals(loginUser.getId())) post.setValidUser(true);
                });
            }

            postList.forEach(post -> {
                post.setFavorite(bookmarkRepository.countBookmarkByPostIdAndSaveIsNull(post.getId()));
                post.setCountComment(commentRepository.countByPostId(post.getId()));
            });
        });

        PagedListHolder<Post> page = new PagedListHolder<>(postList);
        page.setPageSize(pageable.getPageSize());
        page.setPage(pageable.getPageNumber());

        model.addAttribute("totalPage", page.getPageCount() - 1);
        model.addAttribute("post", page.getPageList());

        return "follow-index";
    }

    // 유저 프로필
    @GetLoginInfo
    @GetMapping("/user/{id}")
    public String user(@PathVariable Long id, Model model, @LoginUser SessionUser user) {
        User masterUser = userRepository.findById(id).orElse(null);

        if (user != null) {
            List<Follow> follow =
                    followRepository.findByFollowerIdAndFollowingId(user.getId(), masterUser.getId());

            if (!follow.isEmpty()) masterUser.setValidFollow(true);
        }

        if (masterUser.getCountry().size() == 1) {
            model.addAttribute("isCountrySize", true);
        }

        model.addAttribute("country", countryRepository.findAllByUserIdOrderByCreatedAtDesc(id));
        model.addAttribute("user", masterUser);
        model.addAttribute("countPost", masterUser.getPost().size());
        model.addAttribute("countBookmark",
                bookmarkRepository.countBookmarkByUserIdAndSaveIsNotNull(masterUser.getId()));
        model.addAttribute("countFollower", followRepository.countFollowerIdByFollowingId(id));
        model.addAttribute("countFollowing", followRepository.countFollowingIdByFollowerId(id));

        return "partial/user/user";
    }

    // 팔로잉
    @GetLoginInfo
    @GetMapping("/user/{id}/following")
    public String following(@PathVariable Long id, Model model, @LoginUser SessionUser user) {
        List<Follow> followerList = followRepository.findFollowingIdByFollowerId(id);

        // 팔로잉 목록
        List<User> following = new ArrayList<>();

        followerList.forEach(follow -> {
            following.add(userRepository.findById(follow.getFollowing().getId()).orElse(null));
        });

        // 팔로우 한 상대의 팔로우에 내가 있는지 (맞팔)
        following.forEach(u -> {
            List<Follow> List = followRepository.findFollowingIdByFollowerId(u.getId());
            List.forEach(follow -> {
                if (follow.getFollowing().getId().equals(id)) {
                    u.setValidFollowing(true);
                }
            });
        });

        model.addAttribute("followingUser", following);

        return "partial/user/following";
    }

    // 팔로워
    @GetLoginInfo
    @GetMapping("/user/{id}/follower")
    public String follower(@PathVariable Long id, Model model, @LoginUser SessionUser user) {
        List<Follow> followList = followRepository.findByFollowingId(id);

        // 팔로워 목록
        List<User> followerList = new ArrayList<>();

        followList.forEach(follow ->
                followerList.add(userRepository.findById(follow.getFollower().getId()).orElse(null)));

        // 팔로워 리스트 유저의 팔로워 (맞팔)
        followerList.forEach(u -> {
            List<Follow> List = followRepository.findFollowerIdByFollowingId(u.getId());
            List.forEach(follow -> {
                if (follow.getFollower().getId().equals(id)) {
                    u.setValidFollower(true);
                }
            });
        });

        model.addAttribute("followerUser", followerList);

        return "partial/user/follower";
    }

    // 유저 수정
    @GetLoginInfo
    @GetMapping("/user/{userId}/update")
    public String updateUser(@PathVariable Long userId, Model model, @LoginUser SessionUser user) {
        User masterUser = userRepository.findById(userId).orElse(null);

        model.addAttribute("user", masterUser);

        if (masterUser.getId().equals(user.getId())) {
            return "partial/user/update-user";
        } else {
            return "return";
        }
    }


    // 나라 등록
    @GetLoginInfo
    @GetMapping("/country/create")
    public String createCountry(Model model, @LoginUser SessionUser user) {
        return "partial/country/create-country";
    }

    // 나라 수정
    @GetLoginInfo
    @GetMapping("/country/{id}/update")
    public String updateCountry(@PathVariable Long id, Model model, @LoginUser SessionUser user) {
        Country country = countryRepository.findById(id).orElse(null);

        model.addAttribute("country", country);

        if (country.getUser().getId().equals(user.getId())) {
            return "partial/country/update-country";
        } else {
            return "return";
        }

    }

    // 게시글 등록
    @GetLoginInfo
    @GetMapping("/post/create/{countryId}")
    public String createPost(@PathVariable Long countryId, Model model, @LoginUser SessionUser user) {
        Country country = countryRepository.findById(countryId).orElse(null);

        model.addAttribute("country", country);

        return "partial/post/create-post";
    }

    // 게시글 수정
    @GetLoginInfo
    @GetMapping("/post/{postId}/update")
    public String updatePost(@PathVariable Long postId, Model model, @LoginUser SessionUser user) {
        Post post = postRepository.findById(postId).orElse(null);

        model.addAttribute("post", post);

        if (post.getUser().getId().equals(user.getId())) {
            return "partial/post/update-post";
        } else {
            return "return";
        }
    }

}

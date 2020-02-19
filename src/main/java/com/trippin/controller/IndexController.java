package com.trippin.controller;

import com.trippin.config.auth.LoginUser;
import com.trippin.config.auth.SessionUser;
import com.trippin.domain.*;
import lombok.RequiredArgsConstructor;
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

    // 게시글 상세
    @GetMapping("/post/{postId}")
    public String cardDetail(@PathVariable Long postId, Model model, @LoginUser SessionUser user) {
        Post post = postRepository.findById(postId).orElse(null);
        User masterUser = userRepository.findById(post.getUser().getId()).orElse(null);

        List<Comment> commentList = commentRepository.findByPostIdOrderByCreatedAtDesc(postId);

        if (user != null && masterUser.getId().equals(user.getId())) {
            model.addAttribute("validUser", true);
        }

        if (user != null) {
            model.addAttribute("loginUser", user);
            model.addAttribute("isLogin", true);

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
    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user) {
        List<Post> postList = postRepository.findAllByOrderByCreatedAtDesc();

        if (user != null) {
            model.addAttribute("loginUser", userRepository.getOne(user.getId()));

            model.addAttribute("isLogin", true);

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

        postList.forEach(post -> {
            post.setFavorite(bookmarkRepository.countBookmarkByPostIdAndSaveIsNull(post.getId()));
            post.setCountComment(commentRepository.countByPostId(post.getId()));
        });

        model.addAttribute("post", postList);

        return "index";
    }

    // 유저 게시글 출력
    @GetMapping("/user/{userId}/post")
    public String readUserPost(@PathVariable Long userId, Model model, @LoginUser SessionUser loginUser) {
        List<Post> postList = postRepository.findAllByUserIdOrderByCreatedAtDesc(userId);

        if (loginUser != null) {
            model.addAttribute("loginUser", loginUser);

            model.addAttribute("isLogin", true);

            postList.forEach(post -> {
                List<Bookmark> bookmark =
                        bookmarkRepository.findPostByUserIdAndPostIdAndSaveIsNotNull(loginUser.getId(), post.getId());
                List<Bookmark> favorite =
                        bookmarkRepository.findPostByUserIdAndPostIdAndSaveIsNull(loginUser.getId(), post.getId());
                List<Follow> follow =
                        followRepository.findByFollowerIdAndFollowingId(loginUser.getId(), post.getUser().getId());
                if (!bookmark.isEmpty()) post.setValidBookmark(true);
                if (!favorite.isEmpty()) post.setValidFavorite(true);
                if (!follow.isEmpty()) post.setValidFollow(true);
                if (post.getUser().getId().equals(loginUser.getId())) post.setValidUser(true);
            });
        }

        User user = userRepository.findById(userId).orElse(null);

        postList.forEach(post -> {
            post.setFavorite(bookmarkRepository.countBookmarkByPostIdAndSaveIsNull(post.getId()));
            post.setCountComment(commentRepository.countByPostId(post.getId()));
        });

        if (loginUser != null && user.getId().equals(loginUser.getId())) {
            model.addAttribute("validUser", true);
        }
        model.addAttribute("post", postList);
        model.addAttribute("user", user);

        return "/user-index";
    }

    // 유저 북마크 게시글 출력
    @GetMapping("/user/{userId}/bookmark")
    public String readUserBookmark(@PathVariable Long userId, Model model, @LoginUser SessionUser loginUser) {
        List<Bookmark> bookmarkList = bookmarkRepository.findPostByUserIdAndSaveIsNotNull(userId);

        if (loginUser != null) {
            model.addAttribute("loginUser", loginUser);
            model.addAttribute("isLogin", true);

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

        return "/bookmark-index";
    }

    // 나라 게시글 출력
    @GetMapping("/country/{countryId}")
    public String readCountryPost(@PathVariable Long countryId, Model model, @LoginUser SessionUser user) {
        List<Post> postList = postRepository.findAllByCountryIdOrderByCreatedAtDesc(countryId);

        if (user != null) {
            model.addAttribute("loginUser", user);

            model.addAttribute("isLogin", true);

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

        postList.forEach(post -> {
            post.setFavorite(bookmarkRepository.countBookmarkByPostIdAndSaveIsNull(post.getId()));
            post.setCountComment(commentRepository.countByPostId(post.getId()));
        });

        Country country = countryRepository.findById(countryId).orElse(null);

        if (user != null && country.getUser().getId().equals(user.getId())) {
            model.addAttribute("validUser", true);
        }

        model.addAttribute("post", postList);
        model.addAttribute("country", country);

        return "/country-index";
    }

    // 팔로잉 게시글 출력
    @GetMapping("/user/{userId}/follow")
    public String readFollowingPost(@PathVariable Long userId, Model model, @LoginUser SessionUser loginUser) {
        List<Follow> followList = followRepository.findFollowingIdByFollowerId(userId);
        List<Post> postList = new ArrayList<>();
        followList.forEach(follow -> postList.addAll(follow.getFollowing().getPost()));

        if (loginUser != null) {
            model.addAttribute("loginUser", loginUser);

            model.addAttribute("isLogin", true);

            postList.forEach(post -> {
                List<Bookmark> bookmark =
                        bookmarkRepository.findPostByUserIdAndPostIdAndSaveIsNotNull(loginUser.getId(), post.getId());
                List<Bookmark> favorite =
                        bookmarkRepository.findPostByUserIdAndPostIdAndSaveIsNull(loginUser.getId(), post.getId());
                List<Follow> follow =
                        followRepository.findByFollowerIdAndFollowingId(loginUser.getId(), post.getUser().getId());
                if (!bookmark.isEmpty()) post.setValidBookmark(true);
                if (!favorite.isEmpty()) post.setValidFavorite(true);
                if (!follow.isEmpty()) post.setValidFollow(true);
                if (post.getUser().getId().equals(loginUser.getId())) post.setValidUser(true);
            });
        }

        postList.forEach(post -> {
            post.setFavorite(bookmarkRepository.countBookmarkByPostIdAndSaveIsNull(post.getId()));
            post.setCountComment(commentRepository.countByPostId(post.getId()));
        });

        model.addAttribute("post", postList);

        return "follow-index";
    }

    // 유저 프로필
    @GetMapping("/user/{id}")
    public String user(@PathVariable Long id, Model model, @LoginUser SessionUser user) {
        User masterUser = userRepository.findById(id).orElse(null);

        if (user != null) {
            model.addAttribute("loginUser", user);
            model.addAttribute("isLogin", true);
        }

        if (user != null && masterUser.getId().equals(user.getId())) {
            model.addAttribute("validUser", true);
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
    @GetMapping("/user/{id}/following")
    public String following(@PathVariable Long id, Model model, @LoginUser SessionUser user) {
        User masterUser = userRepository.findById(id).orElse(null);

        if (user != null) {
            model.addAttribute("loginUser", user);
            model.addAttribute("isLogin", true);
        }

        if (user != null && masterUser.getId().equals(user.getId())) {
            model.addAttribute("validUser", true);
        }

        List<Follow> followerList = followRepository.findFollowingIdByFollowerId(masterUser.getId());

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
    @GetMapping("/user/{id}/follower")
    public String follower(@PathVariable Long id, Model model, @LoginUser SessionUser user) {
        User masterUser = userRepository.findById(id).orElse(null);

        if (user != null) {
            model.addAttribute("loginUser", user);
            model.addAttribute("isLogin", true);
        }

        if (user != null && masterUser.getId().equals(user.getId())) {
            model.addAttribute("validUser", true);
        }

        List<Follow> followList = followRepository.findByFollowingId(masterUser.getId());

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
    @GetMapping("/user/{userId}/update")
    public String updateUser(@PathVariable Long userId, Model model, @LoginUser SessionUser user) {
        User masterUser = userRepository.findById(userId).orElse(null);

        if (user != null) {
            model.addAttribute("loginUser", user);
            model.addAttribute("isLogin", true);
        }

        if (user != null && masterUser.getId().equals(user.getId())) {
            model.addAttribute("validUser", true);
        }

        model.addAttribute("user", masterUser);

        return "/partial/user/update-user";
    }


    // 나라 등록
    @GetMapping("/country/create")
    public String createCountry(Model model, @LoginUser SessionUser user) {
        if (user != null) {
            model.addAttribute("loginUser", user);
            model.addAttribute("isLogin", true);
        }

        return "partial/country/create-country";
    }

    // 나라 수정
    @GetMapping("/country/{id}/update")
    public String updateCountry(@PathVariable Long id, Model model, @LoginUser SessionUser user) {
        // TODO: ADD EXCEPTION
        if (user != null) {
            model.addAttribute("loginUser", user);
            model.addAttribute("isLogin", true);
        }

        model.addAttribute("country", countryRepository.findById(id).orElse(null));

        return "partial/country/update-country";
    }

    // 게시글 등록
    @GetMapping("/post/create/{countryId}")
    public String createPost(@PathVariable Long countryId, Model model, @LoginUser SessionUser user) {
        if (user != null) {
            model.addAttribute("loginUser", user);
            model.addAttribute("isLogin", true);
        }

        Country country = countryRepository.findById(countryId).orElse(null);

        model.addAttribute("country", country);

        return "partial/post/create-post";
    }

    // 게시글 수정
    @GetMapping("/post/{postId}/update")
    public String updatePost(@PathVariable Long postId, Model model, @LoginUser SessionUser user) {
        // TODO: ADD EXCEPTION
        if (user != null) {
            model.addAttribute("loginUser", user);
            model.addAttribute("isLogin", true);
        }

        model.addAttribute("post", postRepository.findById(postId).orElse(null));

        return "partial/post/update-post";
    }

}

package com.trippin.controller;

import com.trippin.config.auth.LoginUser;
import com.trippin.config.auth.SessionUser;
import com.trippin.domain.Bookmark;
import com.trippin.domain.BookmarkRepository;
import com.trippin.domain.Country;
import com.trippin.domain.CountryRepository;
import com.trippin.domain.Post;
import com.trippin.domain.PostRepository;
import com.trippin.domain.User;
import com.trippin.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final CountryRepository countryRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final BookmarkRepository bookmarkRepository;

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
                if (!bookmark.isEmpty()) post.setValidBookmark(true);
                if (!favorite.isEmpty()) post.setValidFavorite(true);
            });
        }

        postList.forEach(
                (post) -> post.setFavorite(bookmarkRepository.countBookmarkByPostIdAndSaveIsNull(post.getId())));

        model.addAttribute("post", postList);

        return "index";
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

        model.addAttribute("country", countryRepository.findAllByUserIdOrderByCreatedAtDesc(id));
        model.addAttribute("user", masterUser);
        model.addAttribute("countPost", masterUser.getPost().size());
        model.addAttribute("countBookmark",
                bookmarkRepository.countBookmarkByUserIdAndSaveIsNotNull(masterUser.getId()));

        return "partial/user/user";
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
                if (!bookmark.isEmpty()) post.setValidBookmark(true);
                if (!favorite.isEmpty()) post.setValidFavorite(true);
            });
        }

        User user = userRepository.findById(userId).orElse(null);

        postList.forEach(
                (post) -> post.setFavorite(bookmarkRepository.countBookmarkByPostIdAndSaveIsNull(post.getId())));

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
                if (!favorite.isEmpty()) bookmark.getPost().setValidFavorite(true);
            });
        }

        bookmarkList.forEach((p) ->
                p.getPost().setFavorite(bookmarkRepository.countBookmarkByPostIdAndSaveIsNull(p.getPost().getId())));

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
                if (!bookmark.isEmpty()) post.setValidBookmark(true);
                if (!favorite.isEmpty()) post.setValidFavorite(true);
            });
        }

        Country country = countryRepository.findById(countryId).orElse(null);

        postList.forEach(
                (post) -> post.setFavorite(bookmarkRepository.countBookmarkByPostIdAndSaveIsNull(post.getId())));

        if (user != null && country.getUser().getId().equals(user.getId())) {
            model.addAttribute("validUser", true);
        }

        model.addAttribute("post", postList);
        model.addAttribute("country", country);

        return "/country-index";
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

    // 게시글 상세
    @GetMapping("/card-detail")
    public String cardDetail() {
        return "partial/post/post-detail";
    }

}

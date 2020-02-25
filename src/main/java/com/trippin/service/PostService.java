package com.trippin.service;

import com.trippin.config.auth.LoginUser;
import com.trippin.config.auth.SessionUser;
import com.trippin.controller.dto.PagePostDto;
import com.trippin.domain.Bookmark;
import com.trippin.domain.BookmarkRepository;
import com.trippin.domain.CommentRepository;
import com.trippin.domain.CountryRepository;
import com.trippin.domain.Follow;
import com.trippin.domain.FollowRepository;
import com.trippin.domain.Post;
import com.trippin.domain.PostRepository;
import com.trippin.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class PostService {

    private final PostRepository postRepository;
    private final CountryRepository countryRepository;
    private final UserRepository userRepository;
    private final BookmarkRepository bookmarkRepository;
    private final FollowRepository followRepository;
    private final CommentRepository commentRepository;
    private final S3Service s3Service;

    // Create
    public void createPost(Long userId,
                           Long countryId,
                           MultipartFile image,
                           String region,
                           String content) throws IOException {

        String imgPath = s3Service.upload(image);

        Post post = Post.builder()
                .image(imgPath)
                .region(region.toUpperCase())
                .content(content)
                .country(countryRepository.getOne(countryId))
                .user(userRepository.getOne(userId))
                .build();

        postRepository.save(post);
    }

    // Read all
    public Page<Post> getPost(Pageable pageable) {
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);

        pageable = PageRequest.of(page, 8);

        return postRepository.findAllByOrderByCreatedAtDesc(pageable);
    }

    // Read Search Post
    public Page<Post> getSearchPost(String region, String country, Pageable pageable) {
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);

        pageable = PageRequest.of(page, 9);

        return postRepository.findByRegionContainingOrCountry_NameContaining(region.toUpperCase(),
                country.toUpperCase(), pageable);
    }

    // Read User Post
    public Page<Post> getUserPost(Long userId, Pageable pageable) {
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);

        pageable = PageRequest.of(page, 9);

        return postRepository.findAllByUserIdOrderByCreatedAtDesc(userId, pageable);
    }

    // Read Bookmark Post
    public Page<Bookmark> getBookmarkPost(Long userId, Pageable pageable) {
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);

        pageable = PageRequest.of(page, 9);

        return bookmarkRepository.findPostByUserIdAndSaveIsNotNull(userId, pageable);
    }

    // Read Country Post
    public Page<Post> getCountryPost(Long countryId, Pageable pageable) {
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);

        pageable = PageRequest.of(page, 9);

        return postRepository.findAllByCountryIdOrderByCreatedAtDesc(countryId, pageable);
    }

    // Current Page Post Data
    public List<PagePostDto> getPagePost(int page, @LoginUser SessionUser user) {
        Pageable pageable = PageRequest.of(page - 1, 2);

        List<Post> postList = postRepository.findByOrderByCreatedAtDesc(pageable);
        List<PagePostDto> pagePostDtoList = new ArrayList<>();

        postList.forEach(post -> {
            List<Bookmark> bookmark = new ArrayList<>();
            List<Bookmark> favorite = new ArrayList<>();
            List<Follow> follow = new ArrayList<>();

            if (user != null) {
                bookmark = bookmarkRepository.findPostByUserIdAndPostIdAndSaveIsNotNull(user.getId(), post.getId());
                favorite = bookmarkRepository.findPostByUserIdAndPostIdAndSaveIsNull(user.getId(), post.getId());
                follow = followRepository.findByFollowerIdAndFollowingId(user.getId(), post.getUser().getId());
            }

            PagePostDto pagePostDto = PagePostDto.builder()
                    .postId(post.getId())
                    .image(post.getImage())
                    .region(post.getRegion())
                    .content(post.getContent())
                    .countryName(post.getCountry().getName())
                    .authorName(post.getUser().getName())
                    .userId(post.getUser().getId())
                    .isLogin(user != null ? true : false)
                    .isBookmark(!bookmark.isEmpty() ? true : false)
                    .isFavorite(!favorite.isEmpty() ? true : false)
                    .isFollow(!follow.isEmpty() ? true : false)
                    .isAuthor(user != null && post.getUser().getId().equals(user.getId()) ? true : false)
                    .countFavorite(bookmarkRepository.countBookmarkByPostIdAndSaveIsNull(post.getId()))
                    .countComment(commentRepository.countByPostId(post.getId()))
                    .build();

            pagePostDtoList.add(pagePostDto);
        });

        return pagePostDtoList;
    }

    // Update
    public void updatePost(Long id, MultipartFile image, String region, String content) throws IOException {
        Post post = postRepository.findById(id).orElse(null);

        String imgPath;

        if (image.getOriginalFilename().equals("")) {
            imgPath = post.getImage();
        } else {
            imgPath = s3Service.upload(image);
        }

        post.update(imgPath, region, content);

        postRepository.save(post);
    }

    // Delete
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

}

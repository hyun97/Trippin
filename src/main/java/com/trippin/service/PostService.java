package com.trippin.service;

import com.trippin.controller.dto.PostDto;
import com.trippin.domain.CountryRepository;
import com.trippin.domain.Post;
import com.trippin.domain.PostRepository;
import com.trippin.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class PostService {

    private final PostRepository postRepository;
    private final CountryRepository countryRepository;
    private final UserRepository userRepository;

    // Create
    public void createPost(PostDto postDto) {
        Post post = Post.builder()
                .image(postDto.getImage())
                .region(postDto.getRegion())
                .content(postDto.getContent())
                .favorite(postDto.getFavorite())
                .country(countryRepository.getOne(postDto.getCountryId()))
                .user(userRepository.getOne(postDto.getUserId()))
                .build();

        postRepository.save(post);
    }

    // Read
    public List<PostDto> getPost() {
        List<Post> post = postRepository.findAll();

        return post.stream().map(PostDto::new).collect(Collectors.toList());
    }

    // Update
    public void updatePost(Long id, PostDto postDto) {
        // TODO: ADD EXCEPTION
        Post post = postRepository.findById(id).orElse(null);
        post.update(postDto);
        postRepository.save(post);
    }

    // Delete
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

}

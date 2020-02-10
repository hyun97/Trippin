package com.trippin.service;

import com.trippin.controller.dto.CountryDto;
import com.trippin.controller.dto.PostDto;
import com.trippin.domain.CountryRepository;
import com.trippin.domain.Post;
import com.trippin.domain.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final CountryRepository countryRepository;

    // create
    public void createPost(PostDto postDto) {
        Post post = Post.builder()
                .image(postDto.getImage())
                .region(postDto.getRegion())
                .content(postDto.getContent())
                .favorite(postDto.getFavorite())
                .country(countryRepository.getOne(postDto.getCountryId()))
                .build();

        postRepository.save(post);
    }

    // read
    public List<CountryDto> getPost() {
        return null;
    }

    // update
    public void updatePost(Long id, PostDto postDto) {

    }

}

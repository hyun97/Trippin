package com.trippin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class SearchService {

//    private final PostRepository postRepository;
//
//    public List<PostDto> search(SearchDto searchDto) {
//        List<Post> postList = postRepository
//                .findByRegionContainingOrCountry_NameContaining(
//                        searchDto.getRegion().toUpperCase(),
//                        searchDto.getCountry().toUpperCase()
//                );
//
//        return postList.stream().map(PostDto::new).collect(Collectors.toList());
//    }

}

package com.trippin.controller;

import com.trippin.controller.dto.PostDto;
import com.trippin.controller.dto.SearchDto;
import com.trippin.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/search")
public class SearchController {

    private final SearchService searchService;

    @GetMapping
    public List<PostDto> search(@RequestBody SearchDto searchDto) {
        return searchService.search(searchDto);
    }

}

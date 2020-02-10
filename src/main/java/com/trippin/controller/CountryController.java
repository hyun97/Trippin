package com.trippin.controller;

import com.trippin.controller.dto.CountryDto;
import com.trippin.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/country")
public class CountryController {

    private final CountryService countryService;

    // create
    @PostMapping
    public void createCountry(@RequestBody CountryDto countryDto) {
        countryService.createCountry(countryDto);
    }

    // read
    @GetMapping
    public List<CountryDto> getCountry() {
        return countryService.getCountry();
    }

    // update
    @PutMapping("/{id}")
    public void updateCountry(@PathVariable Long id, @RequestBody CountryDto countryDto) {
        countryService.updateCountry(id, countryDto);
    }

    // delete
    // TODO: 나라와 나라->작성글 까지 모두 삭제
    // Join 된 작성글까지 모두 지우는 방법

}
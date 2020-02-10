package com.trippin.controller;

import com.trippin.controller.dto.CountryDto;
import com.trippin.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
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
    @DeleteMapping("/{id}")
    public void deleteCountry(@PathVariable Long id) {
        countryService.deleteCountry(id);
    }

}

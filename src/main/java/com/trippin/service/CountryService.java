package com.trippin.service;

import com.trippin.controller.dto.CountryDto;
import com.trippin.domain.Country;
import com.trippin.domain.CountryRepository;
import com.trippin.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class CountryService {

    private final CountryRepository countryRepository;
    private final UserRepository userRepository;

    // create
    public void createCountry(CountryDto countryDto) {
        Country country = Country.builder()
                .image(countryDto.getImage())
                .name(countryDto.getName())
                .content(countryDto.getContent())
                .user(userRepository.getOne(countryDto.getUserId()))
                .build();

        countryRepository.save(country);
    }

    // read
    public List<CountryDto> getCountry() {
        List<Country> country = countryRepository.findAll();

        return country.stream().map(CountryDto::new).collect(Collectors.toList());
    }

}

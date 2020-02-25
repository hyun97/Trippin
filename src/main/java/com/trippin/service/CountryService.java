package com.trippin.service;

import com.trippin.controller.dto.CountryDto;
import com.trippin.domain.Country;
import com.trippin.domain.CountryRepository;
import com.trippin.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class CountryService {

    private final CountryRepository countryRepository;
    private final UserRepository userRepository;
    private final S3Service s3Service;

    // create
    public void createCountry(Long userId, String name, String content, String image) {
        Country country = Country.builder()
                .image(image)
                .name(name.toUpperCase())
                .content(content)
                .user(userRepository.getOne(userId))
                .build();

        countryRepository.save(country);
    }

    // read
    public List<CountryDto> getCountry() {
        List<Country> country = countryRepository.findAll();

        return country.stream().map(CountryDto::new).collect(Collectors.toList());
    }

    // update
    public void updateCountry(
            Long countryId,
            Long userId,
            String name,
            String content,
            MultipartFile image) throws IOException {
        Country country = countryRepository.findById(countryId).orElse(null);

        String imgPath;

        if (image.getOriginalFilename().equals("")) {
            imgPath = country.getImage();
        } else {
            imgPath = s3Service.upload(image);
        }

        country.update(name, content, imgPath);

        countryRepository.save(country);
    }

    // delete
    public void deleteCountry(Long id) {
        countryRepository.deleteById(id);
    }

}

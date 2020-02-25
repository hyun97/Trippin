package com.trippin.controller;

import com.trippin.controller.dto.CountryDto;
import com.trippin.service.CountryService;
import com.trippin.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/country")
public class CountryController {

    private final S3Service s3Service;
    private final CountryService countryService;

    // Create
    @PostMapping
    public void createCountry(HttpServletResponse httpServletResponse,
                              Long userId,
                              String name,
                              String content,
                              MultipartFile image) throws IOException {
        String imgPath = s3Service.upload(image);

        countryService.createCountry(userId, name, content, imgPath);

        httpServletResponse.sendRedirect("/user/" + userId);
    }

    // Read
    @GetMapping
    public List<CountryDto> getCountry() {
        return countryService.getCountry();
    }

    // Update
    @PutMapping("/{countryId}")
    public void updateCountry(HttpServletResponse httpServletResponse,
                              @PathVariable Long countryId,
                              Long userId,
                              String name,
                              String content,
                              MultipartFile image) throws IOException {
        countryService.updateCountry(countryId, userId, name, content, image);

        httpServletResponse.sendRedirect("/user/" + userId);
    }

    // Delete
    @DeleteMapping("/{id}")
    public void deleteCountry(@PathVariable Long id) {
        countryService.deleteCountry(id);
    }

}

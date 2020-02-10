package com.trippin.controller;

import com.trippin.domain.CountryRepository;
import com.trippin.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final CountryRepository countryRepository;
    private final UserRepository userRepository;

    // 메인
    @GetMapping("/")
    public String index() {
        return "index";
    }

    // 게시글 상세
    @GetMapping("/card-detail")
    public String cardDetail() {
        return "partial/card-detail";
    }

    // 유저 프로필
    @GetMapping("/user/{id}")
    public String user(@PathVariable Long id, Model model) {
        // TODO: ADD EXCEPTION
        model.addAttribute("user", userRepository.findById(id).orElse(null));
        model.addAttribute("country", countryRepository.findAllByUserIdOrderByCreatedAtDesc(id));
        return "partial/user";
    }

    // 나라 등록
    @GetMapping("/country/create")
    public String createCountry() {
        return "partial/create-country";
    }

    // 나라 수정
    @GetMapping("/country/update/{id}")
    public String updateCountry(@PathVariable Long id, Model model) {
        // TODO: ADD EXCEPTION
        model.addAttribute("country", countryRepository.findById(id).orElse(null));
        return "partial/update-country";
    }

}

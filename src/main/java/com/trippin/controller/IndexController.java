package com.trippin.controller;

import com.trippin.config.auth.LoginUser;
import com.trippin.config.auth.SessionUser;
import com.trippin.domain.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final CountryRepository countryRepository;

    // 메인
    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user) {
        if (user != null) {
            model.addAttribute("user", user);
        }

        return "index";
    }

    // 게시글 상세
    @GetMapping("/card-detail")
    public String cardDetail() {
        return "partial/post/post-detail";
    }

    // 유저 프로필
    @GetMapping("/user/{id}")
    public String user(@PathVariable Long id, Model model, @LoginUser SessionUser user) {
        if (user != null) {
            model.addAttribute("user", user);
        }

        model.addAttribute("country", countryRepository.findAllByUserIdOrderByCreatedAtDesc(id));

        return "partial/user";
    }

    // 나라 등록
    @GetMapping("/country/create")
    public String createCountry(Model model, @LoginUser SessionUser user) {
        if (user != null) {
            model.addAttribute("user", user);
        }

        return "partial/country/create-country";
    }

    // 나라 수정
    @GetMapping("/country/update/{id}")
    public String updateCountry(@PathVariable Long id, Model model, @LoginUser SessionUser user) {
        // TODO: ADD EXCEPTION
        if (user != null) {
            model.addAttribute("user", user);
        }

        model.addAttribute("country", countryRepository.findById(id).orElse(null));
        return "partial/country/update-country";
    }

}

package com.trippin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/card-detail")
    public String cardDetail() {
        return "partial/card-detail";
    }

    @GetMapping("/login")
    public String login() {
        return "partial/login";
    }

}

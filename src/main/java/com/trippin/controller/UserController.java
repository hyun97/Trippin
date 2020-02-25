package com.trippin.controller;

import com.trippin.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    // Update
    @PutMapping("/{userId}")
    public void updateUser(HttpServletResponse httpServletResponse,
                           @PathVariable Long userId,
                           MultipartFile picture,
                           String name,
                           String feel) throws IOException {
        userService.updateUser(userId, picture, name, feel);

        httpServletResponse.sendRedirect("/user/" + userId);
    }

    // Delete
    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
    }

}

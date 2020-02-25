package com.trippin.service;

import com.trippin.domain.User;
import com.trippin.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@Transactional
@Service
public class UserService {

    private final UserRepository userRepository;
    private final S3Service s3Service;

    public void updateUser(Long userId, MultipartFile picture, String name, String feel) throws IOException {
        User user = userRepository.findById(userId).orElse(null);

        String imgPath;

        if (picture.getOriginalFilename().equals("")) {
            imgPath = user.getPicture();
        } else {
            imgPath = s3Service.upload(picture);
        }

        user.update(imgPath, name, feel);

        userRepository.save(user);
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

}

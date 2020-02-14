package com.trippin.service;

import com.trippin.controller.dto.UserDto;
import com.trippin.domain.User;
import com.trippin.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class UserService {

    private final UserRepository userRepository;

    public void updateUser(Long userId, UserDto userDto) {
        User user = userRepository.findById(userId).orElse(null);
        user.update(userDto);
        userRepository.save(user);
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

}

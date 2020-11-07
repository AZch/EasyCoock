package com.wordscreators.easyCook.user.controller;

import com.wordscreators.easyCook.common.exception.ResourceNotFoundException;
import com.wordscreators.easyCook.user.model.User;
import com.wordscreators.easyCook.user.repository.UserRepository;
import com.wordscreators.easyCook.user.security.CurrentUser;
import com.wordscreators.easyCook.user.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/me")
    @PreAuthorize("hasRole('USER')")
    public User getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
        return userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
    }
}

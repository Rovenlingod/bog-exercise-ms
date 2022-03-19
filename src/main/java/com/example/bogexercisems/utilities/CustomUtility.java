package com.example.bogexercisems.utilities;

import com.example.bogexercisems.feign.feignDtos.UserDetailsDTO;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class CustomUtility {
    public static Optional<UserDetailsDTO> getCurrentUser(){
        return Optional.of((UserDetailsDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }
}

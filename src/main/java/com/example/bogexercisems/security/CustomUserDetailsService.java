package com.example.bogexercisems.security;

import com.example.bogexercisems.feign.UserServiceFeign;
import com.example.bogexercisems.feign.feignDtos.UserDTO;
import com.example.bogexercisems.feign.feignDtos.UserDetailsDTO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    private UserServiceFeign userServiceFeign;

    public CustomUserDetailsService(UserServiceFeign userServiceFeign) {
        this.userServiceFeign = userServiceFeign;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetailsDTO userDetails = userServiceFeign.getUserDetailsByLogin(username);

        if (Objects.isNull(userDetails)) {
            throw new UsernameNotFoundException("Username:" + username + " not found");
        }

        return userDetails;
    }
}

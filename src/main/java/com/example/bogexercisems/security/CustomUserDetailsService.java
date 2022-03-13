package com.example.bogexercisems.security;

import com.example.bogexercisems.feign.UserServiceFeign;
import com.example.bogexercisems.feign.feignDtos.UserDTO;
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
        UserDTO userDTO = userServiceFeign.getUserByLogin(username);

        if (Objects.isNull(userDTO)) {
            throw new UsernameNotFoundException("Username:" + username + " not found");
        }

        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("USER");
        List<GrantedAuthority> authorities = Collections.singletonList(authority);

        return new User(username, userDTO.getEncryptedPassword(), authorities);
    }
}

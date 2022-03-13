package com.example.bogexercisems.feign;

import com.example.bogexercisems.feign.feignDtos.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user-service")
public interface UserServiceFeign {

    @GetMapping("/api/user")
    UserDTO getUserByLogin(@RequestParam(name = "login") String login);
}

package com.guilherme.bitWatch.controller;

import com.guilherme.bitWatch.domain.user.RequestUser;
import com.guilherme.bitWatch.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @PostMapping("/register")
    public ResponseEntity<Void> userPOST(@RequestBody RequestUser requestUser){

        service.registerUser(requestUser);

        return ResponseEntity.ok().build();
    }

}

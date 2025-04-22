package com.guilherme.bitWatch.controller;

import com.guilherme.bitWatch.domain.user.RequestUser;
import com.guilherme.bitWatch.domain.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/user")
public class UserController {

    private final UserService service;

    private final AuthController controller;

    public UserController(UserService service, AuthController controller) {
        this.service = service;
        this.controller = controller;
    }

    @PostMapping("/register")
    public ResponseEntity<String> userRegister(@RequestBody RequestUser requestUser){

        this.service.registerUser(requestUser);

        this.controller.authRequest(requestUser);

        return ResponseEntity.status(HttpStatus.OK).body("Code sent successfully to email. Please check your email box.");
    }

}

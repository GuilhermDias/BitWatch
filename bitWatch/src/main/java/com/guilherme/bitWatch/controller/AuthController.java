package com.guilherme.bitWatch.controller;

import com.guilherme.bitWatch.domain.user.RequestUser;
import com.guilherme.bitWatch.domain.user.User;
import com.guilherme.bitWatch.domain.user.UserRepository;
import com.guilherme.bitWatch.infra.security.TokenService;
import com.guilherme.bitWatch.infra.security.auth.AuthService;
import com.guilherme.bitWatch.infra.security.auth.CodeRequest;
import com.guilherme.bitWatch.infra.security.auth.LoginResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/auth")
public class AuthController {

    private final AuthService service;

    private final AuthenticationManager authenticationManager;

    private final TokenService tokenService;

    private final UserRepository repository;

    public AuthController(AuthService service, AuthenticationManager authenticationManager, TokenService tokenService, UserRepository repository) {
        this.service = service;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.repository = repository;
    }

    @PostMapping("/request-code")
    public ResponseEntity<String> authRequest(@RequestBody RequestUser user){
        this.service.RequestCodeAuthentication(user);

        return ResponseEntity.status(HttpStatus.OK).body("Code sent successfully to email.");
    }

    @PostMapping("/validate-code")
    public ResponseEntity<String> authValidateCode(@RequestBody CodeRequest request){
        this.service.validateCode(request.email(), request.code());

        return ResponseEntity.status(HttpStatus.OK).body("Account activated successfully.");
    }

    @PostMapping("/user-login")
    public ResponseEntity authLogin(@RequestBody @Valid RequestUser user){
        if(this.repository.existsByEmailAndIsActiveUserTrue(user.email())) {
            var senhaUsuario = new UsernamePasswordAuthenticationToken(user.email(), user.password());
            var auth = this.authenticationManager.authenticate(senhaUsuario);
            var token = tokenService.tokenGenerate((User) auth.getPrincipal());
            return ResponseEntity.ok(new LoginResponse(token));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The user does not active");
        }
    }

}

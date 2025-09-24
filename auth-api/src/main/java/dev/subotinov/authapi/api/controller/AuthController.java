package dev.subotinov.authapi.api.controller;

import dev.subotinov.authapi.api.dto.JwtResponse;
import dev.subotinov.authapi.api.dto.LoginRequest;
import dev.subotinov.authapi.api.dto.RegisterRequest;
import dev.subotinov.authapi.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("register")
    public ResponseEntity<JwtResponse> register (@Valid @RequestBody RegisterRequest registerRequest) {
        JwtResponse token = authService.register(registerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(token);
    }

    @PostMapping("login")
    public ResponseEntity<JwtResponse> login (@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }
}

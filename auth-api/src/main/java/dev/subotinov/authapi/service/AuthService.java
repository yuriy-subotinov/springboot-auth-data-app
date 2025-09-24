package dev.subotinov.authapi.service;

import dev.subotinov.authapi.api.dto.JwtResponse;
import dev.subotinov.authapi.api.dto.LoginRequest;
import dev.subotinov.authapi.api.dto.RegisterRequest;
import dev.subotinov.authapi.domain.User;
import dev.subotinov.authapi.repo.UserRepository;
import dev.subotinov.authapi.security.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository users;
    private final PasswordEncoder encoder;
    private final JwtService jwt;

    public JwtResponse register(RegisterRequest request) {
        if (users.existsByEmail(request.email())) {
            throw new IllegalArgumentException("Email already in use");
        }

        User user = new User();
        user.setEmail(request.email());
        user.setPasswordHash(encoder.encode(request.password()));
        users.save(user);

        String token = jwt.issueToken(user.getEmail());
        return new JwtResponse(token);
    }

    public JwtResponse login(LoginRequest request) {
        User user = users.findByEmail(request.email()).orElse(null);

        if (user == null || !encoder.matches(request.password(), user.getPasswordHash())) {
            throw new IllegalArgumentException("Invalid credentials");
        }

        String token = jwt.issueToken(user.getEmail());
        return new JwtResponse(token);
    }

}

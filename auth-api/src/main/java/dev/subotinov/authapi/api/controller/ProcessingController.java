package dev.subotinov.authapi.api.controller;

import dev.subotinov.authapi.api.dto.ProcessRequest;
import dev.subotinov.authapi.api.dto.ProcessResponse;
import dev.subotinov.authapi.security.CustomUserDetails;
import dev.subotinov.authapi.service.ProcessingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class ProcessingController {

    private final ProcessingService service;

    @PostMapping("process")
    public ResponseEntity<ProcessResponse> process(@Valid @RequestBody ProcessRequest request,
                                                   @AuthenticationPrincipal CustomUserDetails details) {
        return ResponseEntity.ok(service.process(request, details.getUsername()));
    }
}

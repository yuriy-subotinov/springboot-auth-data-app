package dev.subotinov.dataapi.controller;

import dev.subotinov.dataapi.dto.TransformRequest;
import dev.subotinov.dataapi.dto.TransformResponse;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api", produces = MediaType.APPLICATION_JSON_VALUE)
public class TransformController {

    @PostMapping(path = "transform", consumes = MediaType.APPLICATION_JSON_VALUE)
    public TransformResponse transform(@Valid @RequestBody TransformRequest request) {
        String upperCase = request.text().toUpperCase();
        String reversed = new StringBuilder(upperCase).reverse().toString();
        return new TransformResponse(reversed);
    }
}

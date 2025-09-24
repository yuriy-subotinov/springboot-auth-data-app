package dev.subotinov.authapi.api.error;

import dev.subotinov.authapi.api.dto.ErrorResponse;
import io.jsonwebtoken.JwtException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ErrorResponse handleNotReadable(HttpMessageNotReadableException ex) {
        return err("bad_request", "Malformed JSON or wrong type", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handleValidation(MethodArgumentNotValidException ex) {
        var msg = ex.getBindingResult().getFieldErrors().stream()
                .map(fe -> fe.getField() + ": " + fe.getDefaultMessage())
                .findFirst()
                .orElse("Validation error");
        return err("validation_error", msg, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ErrorResponse handleConstraint(ConstraintViolationException ex) {
        var msg = ex.getConstraintViolations().stream()
                .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                .findFirst()
                .orElse("Validation error");
        return err("validation_error", msg, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(JwtException.class)
    public ErrorResponse handleJwt(JwtException ex) {
        return err("unauthorized", "Invalid or expired token", HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ErrorResponse handleDenied(AccessDeniedException ex) {
        return err("forbidden", "You do not have permission", HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ErrorResponse handleNotFound(NoSuchElementException ex) {
        return err("not_found", ex.getMessage() != null ? ex.getMessage() : "Resource not found",
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ErrorResponse handleConflict(DataIntegrityViolationException ex) {
        return err("conflict", "Data conflict (maybe email already in use)", HttpStatus.CONFLICT);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResponse handleIllegalArg(IllegalArgumentException ex) {
        var msg = ex.getMessage() != null ? ex.getMessage() : "Bad request";
        return err("bad_request", msg, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ErrorResponse handleAny(Exception ex) {
        return err("internal_error", "Unexpected error", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ErrorResponse err(String error, String message, HttpStatus status) {
        return new ErrorResponse(error, message, status.value(), Instant.now());
    }
}

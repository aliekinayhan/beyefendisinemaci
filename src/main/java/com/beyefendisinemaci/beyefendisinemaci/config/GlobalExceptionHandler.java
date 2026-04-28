package com.beyefendisinemaci.beyefendisinemaci.config;

import com.beyefendisinemaci.beyefendisinemaci.movie.exception.MovieNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MovieNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleMovieNotFound(MovieNotFoundException exception, HttpServletRequest request) {
        ErrorResponse error = ErrorResponse.builder().timeStamp(LocalDateTime.now()).status(HttpStatus.NOT_FOUND.value()).message(exception.getMessage()).path(request.getRequestURI()).build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
}

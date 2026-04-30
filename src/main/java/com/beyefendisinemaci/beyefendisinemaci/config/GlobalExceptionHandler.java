package com.beyefendisinemaci.beyefendisinemaci.config;

import com.beyefendisinemaci.beyefendisinemaci.comment.exception.CommentNotFoundException;
import com.beyefendisinemaci.beyefendisinemaci.comment.exception.MovieForCommentNotFoundException;
import com.beyefendisinemaci.beyefendisinemaci.movie.exception.DuplicateMovieException;
import com.beyefendisinemaci.beyefendisinemaci.movie.exception.MovieNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MovieNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleMovieNotFound(MovieNotFoundException exception, HttpServletRequest request) {
        ErrorResponse error = ErrorResponse.builder()
                .timeStamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())
                .message(exception.getMessage())
                .path(request.getRequestURI())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException exception, HttpServletRequest request) {
        ErrorResponse error = ErrorResponse.builder()
                .timeStamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .message(exception.getBindingResult()
                        .getFieldErrors()
                        .stream()
                        .map(error1 -> error1.getField() + ": " + error1.getDefaultMessage())
                        .collect(Collectors.joining(", ")))
                .path(request.getRequestURI())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(DuplicateMovieException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateMovie(DuplicateMovieException exception, HttpServletRequest request) {
        ErrorResponse error = ErrorResponse.builder()
                .timeStamp(LocalDateTime.now())
                .status(HttpStatus.CONFLICT.value())
                .message(exception.getMessage())
                .path(request.getRequestURI())
                .build();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(MovieForCommentNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleMovieForCommentNotFound(MovieForCommentNotFoundException exception, HttpServletRequest request) {
        ErrorResponse error = ErrorResponse.builder()
                .timeStamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())
                .message(exception.getMessage())
                .path(request.getRequestURI())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(CommentNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCommentNotFound(CommentNotFoundException exception,
                                                               HttpServletRequest request) {
        ErrorResponse error = ErrorResponse.builder()
                .timeStamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())
                .message(exception.getMessage())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }


}

package com.beyefendisinemaci.beyefendisinemaci.comment.controller;

import com.beyefendisinemaci.beyefendisinemaci.comment.dto.request.CommentRequestDto;
import com.beyefendisinemaci.beyefendisinemaci.comment.dto.response.CommentResponseDto;
import com.beyefendisinemaci.beyefendisinemaci.comment.service.CommentService;
import com.beyefendisinemaci.beyefendisinemaci.user.entity.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService service;

    @GetMapping("/movies/{movieId}/comments")
    public ResponseEntity<Slice<CommentResponseDto>> getCommentsByMovie(@PathVariable UUID movieId, Pageable pageable) {
        return ResponseEntity.ok(service.getCommentsByMovie(movieId, pageable));
    }

    @GetMapping("/users/{userId}/comments")
    public ResponseEntity<Slice<CommentResponseDto>> getCommentsByUser(@PathVariable UUID userId, Pageable pageable) {
        return ResponseEntity.ok(service.getCommentsByUser(userId, pageable));
    }

    @PostMapping("/movies/{movieId}/comments")
    public ResponseEntity<CommentResponseDto> addComment(@PathVariable UUID movieId, @Valid @RequestBody CommentRequestDto request, @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.addComment(movieId, request, currentUser));
    }

    @PutMapping("/comments/{id}")
    public ResponseEntity<CommentResponseDto> updateComment(@PathVariable UUID id, @Valid @RequestBody CommentRequestDto request) {
        return ResponseEntity.ok(service.updateComment(id, request));
    }

    @DeleteMapping("/comments/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable UUID id) {
        service.deleteCommentById(id);
        return ResponseEntity.noContent().build();
    }


}

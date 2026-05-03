package com.beyefendisinemaci.beyefendisinemaci.watchlist.controller;

import com.beyefendisinemaci.beyefendisinemaci.user.entity.User;
import com.beyefendisinemaci.beyefendisinemaci.watchlist.dto.response.WatchlistResponseDto;
import com.beyefendisinemaci.beyefendisinemaci.watchlist.service.WatchlistService;
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
public class WatchlistController {
    private final WatchlistService service;

    @GetMapping("/watchlist/me")
    public ResponseEntity<Slice<WatchlistResponseDto>> getMyWatchList(@AuthenticationPrincipal User user, Pageable pageable) {
        return ResponseEntity.ok(service.getWatchList(user.getId(), pageable));
    }

    @GetMapping("/watchlist/{userId}")
    public ResponseEntity<Slice<WatchlistResponseDto>> getWatchList(@PathVariable UUID userId, Pageable pageable) {
        return ResponseEntity.ok(service.getWatchList(userId, pageable));
    }

    @PostMapping("/watchlist/{movieId}")
    public ResponseEntity<Void> addToWatchList(@AuthenticationPrincipal User user, @PathVariable UUID movieId) {
        service.addToWatchList(user.getId(),movieId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/watchlist/{movieId}")
    public ResponseEntity<Void> removeFromWatchList(@AuthenticationPrincipal User user, @PathVariable UUID movieId) {
        service.removeFromWatchList(user.getId(),movieId);
        return ResponseEntity.noContent().build();
    }



}

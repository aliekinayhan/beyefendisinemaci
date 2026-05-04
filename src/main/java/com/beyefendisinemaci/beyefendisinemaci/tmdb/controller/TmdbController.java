package com.beyefendisinemaci.beyefendisinemaci.tmdb.controller;

import com.beyefendisinemaci.beyefendisinemaci.tmdb.dto.TmdbMovieDto;
import com.beyefendisinemaci.beyefendisinemaci.tmdb.service.TmdbService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tmdb")
@RequiredArgsConstructor
public class TmdbController {
    private final TmdbService service;

    @GetMapping("/search")
    public ResponseEntity<List<TmdbMovieDto>> searchMovies(@RequestParam String q) {
        return ResponseEntity.ok(service.searchMovies(q));
    }

}

package com.beyefendisinemaci.beyefendisinemaci.movie.controller;

import com.beyefendisinemaci.beyefendisinemaci.movie.dto.request.MovieRequestDto;
import com.beyefendisinemaci.beyefendisinemaci.movie.dto.response.MovieResponseDto;
import com.beyefendisinemaci.beyefendisinemaci.movie.service.MovieService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService service;

    @GetMapping()
    public ResponseEntity<List<MovieResponseDto>> getAllMovies() {
        return ResponseEntity.ok(service.getAllMovies());
    }

    @GetMapping("/search")
    public ResponseEntity<List<MovieResponseDto>> getMovieByTitle(@RequestParam String q) {
        return ResponseEntity.ok(service.getMovieByTitle(q));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieResponseDto> getMovieById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getMovieById(id));
    }

    @PostMapping()
    public ResponseEntity<MovieResponseDto> createMovie(@Valid @RequestBody MovieRequestDto movie) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createMovie(movie));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovieResponseDto> updateMovie(@PathVariable UUID id, @Valid @RequestBody MovieRequestDto movie) {
        return ResponseEntity.ok(service.updateMovie(id, movie));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable UUID id) {
        service.deleteMovieById(id);
        return ResponseEntity.noContent().build();
    }
}

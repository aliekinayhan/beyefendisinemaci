package com.beyefendisinemaci.beyefendisinemaci.movie.controller;

import com.beyefendisinemaci.beyefendisinemaci.movie.dto.request.MovieRequestDto;
import com.beyefendisinemaci.beyefendisinemaci.movie.dto.response.MovieResponseDto;
import com.beyefendisinemaci.beyefendisinemaci.movie.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService service;

    @GetMapping()
    public List<MovieResponseDto> getAllMovies() {
        return service.getAllMovies();
    }

    @GetMapping("/search")
    public List<MovieResponseDto> getMovieByTitle(@RequestParam String q) {
        return service.getMovieByTitle(q);
    }

    @GetMapping("/{id}")
    public MovieResponseDto getMovieById(@PathVariable UUID id) {
        return service.getMovieById(id);
    }

    @PostMapping()
    public MovieResponseDto createMovie(@RequestBody MovieRequestDto movie) {
        return service.createMovie(movie);
    }

    @PutMapping("/{id}")
    public MovieResponseDto updateMovie(@PathVariable UUID id, @RequestBody MovieRequestDto movie) {
        return service.updateMovie(id,movie);
    }

    @DeleteMapping("/{id}")
    public void deleteMovie (@PathVariable UUID id){
        service.deleteMovieById(id);
    }
}

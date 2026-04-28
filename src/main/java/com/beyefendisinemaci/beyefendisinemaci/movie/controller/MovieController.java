package com.beyefendisinemaci.beyefendisinemaci.movie.controller;

import com.beyefendisinemaci.beyefendisinemaci.movie.entity.Movie;
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
    public List<Movie> getAllMovies() {
        return service.getAllMovies();
    }

    @GetMapping("/search")
    public List<Movie> getMovieByTitle(@RequestParam String title) {
        return service.getMovieByTitle(title);
    }

    @GetMapping("/{id}")
    public Movie getMovieById(@PathVariable UUID id) {
        return service.getMovieById(id);
    }

    @PostMapping()
    public Movie createMovie(@RequestBody Movie movie) {
        return service.createMovie(movie);
    }

    @PutMapping("/{id}")
    public Movie updateMovie(@PathVariable UUID id, @RequestBody Movie movie) {
        return service.updateMovie(id,movie);
    }

    @DeleteMapping("/{id}")
    public void deleteMovie (@PathVariable UUID id){
        service.deleteMovieById(id);
    }
}

package com.beyefendisinemaci.beyefendisinemaci.movie.service;

import com.beyefendisinemaci.beyefendisinemaci.movie.entity.Movie;
import com.beyefendisinemaci.beyefendisinemaci.movie.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor // Constructor Injection
public class MovieService {

    private final MovieRepository repository;

    public List<Movie> getAllMovies() {
        return repository.findAll();
    }

    public List<Movie> getMovieByTitle(String title) {
        return repository.findByTitleContainingIgnoreCase(title);
    }

    public Movie getMovieById(UUID id) {
        return repository.findById(id).orElseThrow();
    }

    public Movie createMovie(Movie movie) {
        return repository.save(movie);
    }

    // Find existing movie via id, update fields from updated movie
    public Movie updateMovie(UUID id, Movie updatedMovie) {
        Movie existingMovie = repository.findById(id).orElseThrow();
        existingMovie.setTitle(updatedMovie.getTitle());
        existingMovie.setGenre(updatedMovie.getGenre());
        existingMovie.setPosterUrl(updatedMovie.getPosterUrl());
        existingMovie.setReview(updatedMovie.getReview());
        existingMovie.setVideoUrl(updatedMovie.getVideoUrl());
        existingMovie.setReleaseYear(updatedMovie.getReleaseYear());
        existingMovie.setTmdbId(updatedMovie.getTmdbId());
        return repository.save(existingMovie);
    }

    public void deleteMovieById(UUID id) {
        repository.deleteById(id);
    }


}

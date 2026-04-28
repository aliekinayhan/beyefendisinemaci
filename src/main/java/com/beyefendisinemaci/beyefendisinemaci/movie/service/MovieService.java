package com.beyefendisinemaci.beyefendisinemaci.movie.service;

import com.beyefendisinemaci.beyefendisinemaci.movie.dto.request.MovieRequestDto;
import com.beyefendisinemaci.beyefendisinemaci.movie.dto.response.MovieResponseDto;
import com.beyefendisinemaci.beyefendisinemaci.movie.entity.Movie;
import com.beyefendisinemaci.beyefendisinemaci.movie.exception.MovieNotFoundException;
import com.beyefendisinemaci.beyefendisinemaci.movie.mapper.MovieMapper;
import com.beyefendisinemaci.beyefendisinemaci.movie.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieMapper mapper;
    private final MovieRepository repository;

    public List<MovieResponseDto> getAllMovies() {
        return repository.findAll().stream().map(mapper::toResponseDto).collect(Collectors.toList());
    }

    public List<MovieResponseDto> getMovieByTitle(String title) {
        return repository.findByTitleContainingIgnoreCase(title).stream().map(mapper::toResponseDto).collect(Collectors.toList());
    }

    public MovieResponseDto getMovieById(UUID id) {
        return mapper.toResponseDto(repository.findById(id).orElseThrow(() -> new MovieNotFoundException(id)));
    }

    public MovieResponseDto createMovie(MovieRequestDto movie) {
        return mapper.toResponseDto(repository.save(mapper.toEntity(movie)));
    }

    // Find existing movie via id, update fields from updated movie
    public MovieResponseDto updateMovie(UUID id, MovieRequestDto updatedMovie) {
        Movie existingMovie = repository.findById(id).orElseThrow(() -> new MovieNotFoundException(id));
        existingMovie.setTitle(updatedMovie.getTitle());
        existingMovie.setGenre(updatedMovie.getGenre());
        existingMovie.setPosterUrl(updatedMovie.getPosterUrl());
        existingMovie.setReview(updatedMovie.getReview());
        existingMovie.setVideoUrl(updatedMovie.getVideoUrl());
        existingMovie.setReleaseYear(updatedMovie.getReleaseYear());
        existingMovie.setTmdbId(updatedMovie.getTmdbId());
        return mapper.toResponseDto(repository.save(existingMovie));
    }

    public void deleteMovieById(UUID id) {
        repository.deleteById(id);
    }


}

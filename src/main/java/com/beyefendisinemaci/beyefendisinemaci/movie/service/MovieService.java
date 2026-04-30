package com.beyefendisinemaci.beyefendisinemaci.movie.service;

import com.beyefendisinemaci.beyefendisinemaci.movie.dto.request.MovieRequestDto;
import com.beyefendisinemaci.beyefendisinemaci.movie.dto.response.MovieResponseDto;
import com.beyefendisinemaci.beyefendisinemaci.movie.entity.Movie;
import com.beyefendisinemaci.beyefendisinemaci.movie.exception.DuplicateMovieException;
import com.beyefendisinemaci.beyefendisinemaci.movie.exception.MovieNotFoundException;
import com.beyefendisinemaci.beyefendisinemaci.movie.exception.TmdbIdMismatchException;
import com.beyefendisinemaci.beyefendisinemaci.movie.mapper.MovieMapper;
import com.beyefendisinemaci.beyefendisinemaci.movie.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieMapper mapper;
    private final MovieRepository repository;

    public Page<MovieResponseDto> getAllMovies(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toResponseDto);
    }

    public List<MovieResponseDto> getMovieByTitle(String title) {
        return repository.findByTitleContainingIgnoreCase(title).stream().map(mapper::toResponseDto).toList();
    }

    public MovieResponseDto getMovieById(UUID id) {
        return mapper.toResponseDto(repository.findById(id).orElseThrow(() -> new MovieNotFoundException(id)));
    }

    public MovieResponseDto createMovie(MovieRequestDto movie) {
        if (repository.existsByTmdbId(movie.getTmdbId())) {
            throw new DuplicateMovieException(movie.getTmdbId());
        }
        return mapper.toResponseDto(repository.save(mapper.toEntity(movie)));
    }


    public MovieResponseDto updateMovie(UUID id, MovieRequestDto updatedMovie) {
        Movie existingMovie = repository.findById(id).orElseThrow(() -> new MovieNotFoundException(id));
        if (!updatedMovie.getTmdbId().equals(existingMovie.getTmdbId()))
            throw new TmdbIdMismatchException(updatedMovie.getTmdbId());

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
        repository.findById(id).orElseThrow(() -> new MovieNotFoundException(id));
        repository.deleteById(id);
    }

    public List<MovieResponseDto> getRecentMovies() {
        return repository.findTop6ByOrderByCreatedAtDesc().stream().map(mapper::toResponseDto).toList();
    }

}

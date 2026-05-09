package com.beyefendisinemaci.beyefendisinemaci.movie.service;

import com.beyefendisinemaci.beyefendisinemaci.comment.service.CommentService;
import com.beyefendisinemaci.beyefendisinemaci.movie.dto.request.MovieRequestDto;
import com.beyefendisinemaci.beyefendisinemaci.movie.dto.response.MovieResponseDto;
import com.beyefendisinemaci.beyefendisinemaci.movie.entity.Movie;
import com.beyefendisinemaci.beyefendisinemaci.movie.exception.DuplicateMovieException;
import com.beyefendisinemaci.beyefendisinemaci.movie.exception.MovieNotFoundException;
import com.beyefendisinemaci.beyefendisinemaci.movie.exception.TmdbIdMismatchException;
import com.beyefendisinemaci.beyefendisinemaci.movie.mapper.MovieMapper;
import com.beyefendisinemaci.beyefendisinemaci.movie.repository.MovieRepository;
import com.beyefendisinemaci.beyefendisinemaci.watchlist.service.WatchlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieMapper mapper;
    private final MovieRepository movieRepository;
    private final CommentService commentService;
    private final WatchlistService watchlistService;

    @Transactional(readOnly = true)
    public Page<MovieResponseDto> getAllMovies(Pageable pageable) {
        return movieRepository.findAll(pageable).map(mapper::toResponseDto);
    }

    @Transactional(readOnly = true)
    public List<MovieResponseDto> getMovieByTitle(String title) {
        return movieRepository.findByTitleContainingIgnoreCase(title).stream().map(mapper::toResponseDto).toList();
    }

    @Transactional(readOnly = true)
    public MovieResponseDto getMovieById(UUID id) {
        return mapper.toResponseDto(movieRepository.findById(id).orElseThrow(() -> new MovieNotFoundException(id)));
    }

    @Transactional
    public MovieResponseDto createMovie(MovieRequestDto movie) {
        if (movieRepository.existsByTmdbId(movie.getTmdbId())) {
            throw new DuplicateMovieException(movie.getTmdbId());
        }
        return mapper.toResponseDto(movieRepository.save(mapper.toEntity(movie)));
    }

    @Transactional
    public MovieResponseDto updateMovie(UUID id, MovieRequestDto updatedMovie) {
        Movie existingMovie = movieRepository.findById(id).orElseThrow(() -> new MovieNotFoundException(id));
        if (!updatedMovie.getTmdbId().equals(existingMovie.getTmdbId()))
            throw new TmdbIdMismatchException(updatedMovie.getTmdbId());
        existingMovie.setTitle(updatedMovie.getTitle());
        existingMovie.setPosterUrl(updatedMovie.getPosterUrl());
        existingMovie.setShortVideoUrl(updatedMovie.getShortVideoUrl());
        existingMovie.setVideoUrl(updatedMovie.getVideoUrl());
        existingMovie.setGenre(updatedMovie.getGenre());
        existingMovie.setReleaseYear(updatedMovie.getReleaseYear());
        existingMovie.setTmdbId(updatedMovie.getTmdbId());
        existingMovie.setReview(updatedMovie.getReview());
        return mapper.toResponseDto(movieRepository.save(existingMovie));
    }

    @Transactional
    public void deleteMovieById(UUID movieId) {
        Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new MovieNotFoundException(movieId));
        commentService.deleteByMovieId(movieId);
        watchlistService.deleteByMovieId(movieId);
        movieRepository.delete(movie);
    }

    @Transactional(readOnly = true)
    public List<MovieResponseDto> getRecentMovies() {
        return movieRepository.findTop6ByOrderByCreatedAtDesc().stream().map(mapper::toResponseDto).toList();
    }

    public Movie getMovieEntity(UUID movieId) {
        return movieRepository.findById(movieId).orElseThrow(() -> new MovieNotFoundException(movieId));
    }
}

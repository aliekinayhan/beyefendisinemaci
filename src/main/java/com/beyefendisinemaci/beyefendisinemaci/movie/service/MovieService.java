package com.beyefendisinemaci.beyefendisinemaci.movie.service;

import com.beyefendisinemaci.beyefendisinemaci.comment.service.CommentService;
import com.beyefendisinemaci.beyefendisinemaci.movie.dto.request.MovieRequestDto;
import com.beyefendisinemaci.beyefendisinemaci.movie.dto.response.MovieResponseDto;
import com.beyefendisinemaci.beyefendisinemaci.movie.entity.Movie;
import com.beyefendisinemaci.beyefendisinemaci.movie.exception.DuplicateMovieException;
import com.beyefendisinemaci.beyefendisinemaci.movie.exception.MovieNotFoundException;
import com.beyefendisinemaci.beyefendisinemaci.movie.mapper.MovieMapper;
import com.beyefendisinemaci.beyefendisinemaci.movie.repository.MovieRepository;
import com.beyefendisinemaci.beyefendisinemaci.redis.service.RedisSearchService;
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
    private final RedisSearchService redisSearchService;

    @Transactional(readOnly = true)
    public Page<MovieResponseDto> getAllMovies(Pageable pageable) {
        return movieRepository.findAll(pageable).map(mapper::toResponseDto);
    }

    @Transactional(readOnly = true)
    public List<MovieResponseDto> getMovieByTitle(String title) {
        redisSearchService.recordSearch(title);

        List<MovieResponseDto> cached = redisSearchService.getCachedSearchResult(title);
        if (cached != null) {
            return cached;
        }

        List<MovieResponseDto> results = movieRepository
                .findByTitleContainingIgnoreCaseOrderByCreatedAtDesc(title)
                .stream()
                .map(mapper::toResponseDto)
                .toList();

        redisSearchService.cacheSearchResult(title, results);
        return results;
    }

    @Transactional(readOnly = true)
    public List<MovieResponseDto> getMovieByTitle2(String title) {
        return movieRepository
                .findByTitleContainingIgnoreCaseOrderByCreatedAtDesc(title)
                .stream()
                .map(mapper::toResponseDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public MovieResponseDto getMovieById(UUID movieId) {
        return mapper.toResponseDto(findByMovieId(movieId));
    }

    @Transactional
    public MovieResponseDto createMovie(MovieRequestDto movie) {
        if (movieRepository.existsByTmdbId(movie.getTmdbId())) {
            throw new DuplicateMovieException(movie.getTmdbId());
        }
        redisSearchService.evictAllSearchCache();
        return mapper.toResponseDto(movieRepository.save(mapper.toEntity(movie)));
    }

    @Transactional
    public MovieResponseDto updateMovie(UUID movieId, MovieRequestDto updatedMovie) {
        Movie existingMovie = findByMovieId(movieId);
        updateMovie(updatedMovie, existingMovie);
        return mapper.toResponseDto(movieRepository.save(existingMovie));
    }

    @Transactional
    public void deleteMovieById(UUID movieId) {
        Movie movie = findByMovieId(movieId);
        redisSearchService.evictAllSearchCache();
        deleteMovie(movie);
    }

    @Transactional(readOnly = true)
    public List<MovieResponseDto> getRecentMovies() {
        return movieRepository.findTop6ByOrderByCreatedAtDesc().stream().map(mapper::toResponseDto).toList();
    }

    private Movie findByMovieId(UUID movieId) {
        return movieRepository.findById(movieId).orElseThrow(() -> new MovieNotFoundException(movieId));
    }

    private void updateMovie(MovieRequestDto updatedMovie, Movie existingMovie) {
        existingMovie.setTitle(updatedMovie.getTitle());
        existingMovie.setPosterUrl(updatedMovie.getPosterUrl());
        existingMovie.setShortVideoUrl(updatedMovie.getShortVideoUrl());
        existingMovie.setVideoUrl(updatedMovie.getVideoUrl());
        existingMovie.setGenre(updatedMovie.getGenre());
        existingMovie.setReleaseYear(updatedMovie.getReleaseYear());
        existingMovie.setTmdbId(updatedMovie.getTmdbId());
        existingMovie.setReview(updatedMovie.getReview());
        existingMovie.setOriginalTitle(updatedMovie.getOriginalTitle());
    }

    private void deleteMovie(Movie movie) {
        commentService.deleteByMovieId(movie.getId());
        watchlistService.deleteByMovieId(movie.getId());
        movieRepository.delete(movie);
    }


    //REDIS
    public List<String> getTrendingSearches() {
        return redisSearchService.getTrendingSearches();
    }
}

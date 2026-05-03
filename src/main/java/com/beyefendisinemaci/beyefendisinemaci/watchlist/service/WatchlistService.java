package com.beyefendisinemaci.beyefendisinemaci.watchlist.service;

import com.beyefendisinemaci.beyefendisinemaci.movie.entity.Movie;
import com.beyefendisinemaci.beyefendisinemaci.movie.exception.MovieNotFoundException;
import com.beyefendisinemaci.beyefendisinemaci.movie.repository.MovieRepository;
import com.beyefendisinemaci.beyefendisinemaci.user.entity.User;
import com.beyefendisinemaci.beyefendisinemaci.user.exception.UserNotFoundException;
import com.beyefendisinemaci.beyefendisinemaci.user.repository.UserRepository;
import com.beyefendisinemaci.beyefendisinemaci.watchlist.dto.response.WatchlistResponseDto;
import com.beyefendisinemaci.beyefendisinemaci.watchlist.entity.Watchlist;
import com.beyefendisinemaci.beyefendisinemaci.watchlist.exception.AlreadyExistsOnListException;
import com.beyefendisinemaci.beyefendisinemaci.watchlist.mapper.WatchlistMapper;
import com.beyefendisinemaci.beyefendisinemaci.watchlist.repository.WatchlistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WatchlistService {
    private final WatchlistRepository repository;
    private final UserRepository userRepository;
    private final MovieRepository movieRepository;
    private final WatchlistMapper mapper;

    public Slice<WatchlistResponseDto> getWatchList(UUID userId, Pageable pageable) {
        return repository.findByUserId(userId, pageable).map(mapper::toResponseDto);
    }

    public void addToWatchList(UUID userId, UUID movieId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new MovieNotFoundException(movieId));
        if (repository.existsByUserIdAndMovieId(userId, movieId)) {
            throw new AlreadyExistsOnListException();
        }
        Watchlist item = Watchlist.builder()
                .user(user)
                .movie(movie)
                .build();
        repository.save(item);

    }

    public void removeFromWatchList(UUID userId, UUID movieId) {
        repository.deleteByUserIdAndMovieId(userId, movieId);
    }
}

package com.beyefendisinemaci.beyefendisinemaci.movie.exception;

public class DuplicateMovieException extends RuntimeException {

    public DuplicateMovieException(Integer tmdbId) {
        super("The Movie Already Exists: " + tmdbId);
    }
}

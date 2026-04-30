package com.beyefendisinemaci.beyefendisinemaci.movie.exception;

public class TmdbIdMismatchException extends RuntimeException{
    public TmdbIdMismatchException(Integer tmdbId) {
        super("TmdbId does not match: " + tmdbId);
    }
}

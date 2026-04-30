package com.beyefendisinemaci.beyefendisinemaci.comment.exception;

import java.util.UUID;

public class MovieForCommentNotFoundException extends RuntimeException {
    public MovieForCommentNotFoundException(UUID movieId) {
        super("Cannot add comment, movie not found with id: " + movieId);
    }
}

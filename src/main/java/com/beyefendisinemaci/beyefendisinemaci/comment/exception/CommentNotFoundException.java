package com.beyefendisinemaci.beyefendisinemaci.comment.exception;

import java.util.UUID;

public class CommentNotFoundException extends RuntimeException{
    public CommentNotFoundException(UUID commentId) {
        super("Comment not found with id: " + commentId);
    }
}

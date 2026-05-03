package com.beyefendisinemaci.beyefendisinemaci.user.exception;

import java.util.UUID;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(UUID id) {
        super("User Not Found: " + id);
    }
}

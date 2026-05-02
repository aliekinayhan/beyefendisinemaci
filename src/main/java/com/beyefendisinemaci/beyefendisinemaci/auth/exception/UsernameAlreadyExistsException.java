package com.beyefendisinemaci.beyefendisinemaci.auth.exception;

public class UsernameAlreadyExistsException extends RuntimeException{
    public UsernameAlreadyExistsException(String username) {
        super("The Username is Already Exists: " + username );
    }
}

package com.beyefendisinemaci.beyefendisinemaci.auth.exception;

public class EmailAlreadyExistsException extends RuntimeException{
    public EmailAlreadyExistsException(String email) {
        super("The E-mail is Already Exists: " + email );

    }
}

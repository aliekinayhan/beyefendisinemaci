package com.beyefendisinemaci.beyefendisinemaci.s3.exception;

public class InvalidFileExtensionException extends RuntimeException {
    public InvalidFileExtensionException() {
        super("Wrong Extension Type");
    }
}

package com.beyefendisinemaci.beyefendisinemaci.s3.exception;

public class InvalidFileTypeException extends RuntimeException {
    public InvalidFileTypeException() {
        super("Wrong File Type");
    }
}

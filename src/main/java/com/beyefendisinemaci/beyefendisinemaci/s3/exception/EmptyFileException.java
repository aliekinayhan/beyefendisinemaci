package com.beyefendisinemaci.beyefendisinemaci.s3.exception;

public class EmptyFileException extends RuntimeException {
    public EmptyFileException() {
        super("File can not be empty");
    }
}

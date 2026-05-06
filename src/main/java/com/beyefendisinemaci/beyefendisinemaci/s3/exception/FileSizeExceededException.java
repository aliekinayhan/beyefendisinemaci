package com.beyefendisinemaci.beyefendisinemaci.s3.exception;

public class FileSizeExceededException extends RuntimeException {
    public FileSizeExceededException(String message) {
        super("File Size Exceeded: " + message);
    }
}

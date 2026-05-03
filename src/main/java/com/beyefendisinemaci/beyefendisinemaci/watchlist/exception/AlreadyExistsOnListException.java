package com.beyefendisinemaci.beyefendisinemaci.watchlist.exception;

public class AlreadyExistsOnListException extends RuntimeException {
    public AlreadyExistsOnListException() {
        super("The Movie Already Exists In Your Watch List");
    }
}

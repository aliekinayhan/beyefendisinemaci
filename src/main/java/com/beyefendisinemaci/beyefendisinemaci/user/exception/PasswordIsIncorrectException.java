package com.beyefendisinemaci.beyefendisinemaci.user.exception;

public class PasswordIsIncorrectException extends RuntimeException {
  public PasswordIsIncorrectException() {
    super("Password is incorrect");
  }
}

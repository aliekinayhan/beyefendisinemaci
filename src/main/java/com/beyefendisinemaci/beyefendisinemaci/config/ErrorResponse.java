package com.beyefendisinemaci.beyefendisinemaci.config;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorResponse {
    private String message;
    private int status;
    private String path;
    private LocalDateTime timeStamp;
}

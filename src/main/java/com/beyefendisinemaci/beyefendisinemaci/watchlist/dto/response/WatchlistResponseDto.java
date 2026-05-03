package com.beyefendisinemaci.beyefendisinemaci.watchlist.dto.response;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WatchlistResponseDto {
    private UUID movieId;
    private String title;
    private String posterUrl;
}

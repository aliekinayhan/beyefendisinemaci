package com.beyefendisinemaci.beyefendisinemaci.tmdb.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TmdbMovieDto {
    private Integer tmdbId;
    private String title;
    private String posterUrl;
    private String releaseYear;
    private String originalTitle ;
}

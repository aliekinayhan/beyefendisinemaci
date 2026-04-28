package com.beyefendisinemaci.beyefendisinemaci.movie.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieRequestDto {

    @NotBlank
    private String title;
    private String posterUrl;
    private String videoUrl;
    private String genre;
    private Integer releaseYear;
    private Integer tmdbId;
    @NotBlank
    private String review;
}

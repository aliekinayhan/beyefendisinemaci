package com.beyefendisinemaci.beyefendisinemaci.tmdb.service;

import com.beyefendisinemaci.beyefendisinemaci.tmdb.dto.TmdbMovieDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class TmdbService {

    @Value("${tmdb.api-key}")
    private String apiKey;

    private final RestClient restClient = RestClient.create();

    public List<TmdbMovieDto> searchMovies(String query) {
        TmdbSearchResponse response = restClient.get()
                .uri("https://api.themoviedb.org/3/search/movie?query={query}&api_key={apiKey}&language=tr-TR", query, apiKey)
                .retrieve()
                .body(TmdbSearchResponse.class);

        return response.getResults().stream().map(movie -> TmdbMovieDto.builder()
                .tmdbId(movie.getId())
                .title(movie.getTitle())
                .posterUrl("https://image.tmdb.org/t/p/w500" + movie.getPosterPath())
                .releaseYear(movie.getReleaseDate() != null && !movie.getReleaseDate().isEmpty()
                        ? movie.getReleaseDate().substring(0, 4)
                        : null)
                .build())
                .toList();
    }


    // Inner classes for to have results in the structure I want
    @Getter
    static class TmdbSearchResponse {
        private List<TmdbMovieResult> results;
    }

    @Getter
    static class TmdbMovieResult {
        private Integer id;
        private String title;
        @JsonProperty("poster_path")
        private String posterPath;
        @JsonProperty("release_date")
        private String releaseDate;
    }
}



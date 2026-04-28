package com.beyefendisinemaci.beyefendisinemaci.movie.mapper;

import com.beyefendisinemaci.beyefendisinemaci.movie.dto.request.MovieRequestDto;
import com.beyefendisinemaci.beyefendisinemaci.movie.dto.response.MovieResponseDto;
import com.beyefendisinemaci.beyefendisinemaci.movie.entity.Movie;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MovieMapper {
    MovieResponseDto toResponseDto(Movie movie);
    Movie toEntity(MovieRequestDto requestDto);
}

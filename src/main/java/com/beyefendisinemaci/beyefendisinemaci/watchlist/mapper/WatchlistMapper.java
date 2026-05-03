package com.beyefendisinemaci.beyefendisinemaci.watchlist.mapper;

import com.beyefendisinemaci.beyefendisinemaci.watchlist.dto.response.WatchlistResponseDto;
import com.beyefendisinemaci.beyefendisinemaci.watchlist.entity.Watchlist;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface WatchlistMapper {
    @Mapping(source = "movie.id", target = "movieId")
    @Mapping(source = "movie.title", target = "title")
    @Mapping(source = "movie.posterUrl", target = "posterUrl")
    WatchlistResponseDto toResponseDto(Watchlist watchlist);
}

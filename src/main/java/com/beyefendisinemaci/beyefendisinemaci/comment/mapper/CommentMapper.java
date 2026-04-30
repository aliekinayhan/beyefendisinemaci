package com.beyefendisinemaci.beyefendisinemaci.comment.mapper;

import com.beyefendisinemaci.beyefendisinemaci.comment.dto.request.CommentRequestDto;
import com.beyefendisinemaci.beyefendisinemaci.comment.dto.response.CommentResponseDto;
import com.beyefendisinemaci.beyefendisinemaci.comment.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    @Mapping(source = "movie.id",target = "movieId")
    @Mapping(source = "user.id",target = "userId")
    @Mapping(source = "user.username",target = "username")
    @Mapping(source = "user.profilePhoto",target = "userProfilePhoto")
    CommentResponseDto toResponseDto (Comment comment);
    Comment toEntity (CommentRequestDto requestDto);
}

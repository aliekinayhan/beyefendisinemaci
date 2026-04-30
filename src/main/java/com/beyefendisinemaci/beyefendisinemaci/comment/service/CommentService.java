package com.beyefendisinemaci.beyefendisinemaci.comment.service;

import com.beyefendisinemaci.beyefendisinemaci.comment.dto.request.CommentRequestDto;
import com.beyefendisinemaci.beyefendisinemaci.comment.dto.response.CommentResponseDto;
import com.beyefendisinemaci.beyefendisinemaci.comment.entity.Comment;
import com.beyefendisinemaci.beyefendisinemaci.comment.exception.CommentNotFoundException;
import com.beyefendisinemaci.beyefendisinemaci.comment.exception.MovieForCommentNotFoundException;
import com.beyefendisinemaci.beyefendisinemaci.comment.mapper.CommentMapper;
import com.beyefendisinemaci.beyefendisinemaci.comment.repository.CommentRepository;
import com.beyefendisinemaci.beyefendisinemaci.movie.entity.Movie;
import com.beyefendisinemaci.beyefendisinemaci.movie.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentMapper mapper;
    private final CommentRepository repository;
    private final MovieRepository movieRepository;

    public Slice<CommentResponseDto> getCommentsByMovie(UUID id, Pageable pageable) {
        return repository.findByMovieId(id, pageable).map(mapper::toResponseDto);
    }

    public Slice<CommentResponseDto> getCommentsByUser(UUID id, Pageable pageable) {
        return repository.findByUserId(id, pageable).map(mapper::toResponseDto);
    }

    public CommentResponseDto addComment(UUID movieId, CommentRequestDto requestDto) {
        Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new MovieForCommentNotFoundException(movieId));
        Comment comment = Comment.builder()
                .content(requestDto.getContent())
                .movie(movie)
                .build();
        return mapper.toResponseDto(repository.save(comment));
    }

    public CommentResponseDto updateComment(UUID commentId, CommentRequestDto requestDto) {
        Comment existingComment = repository.findById(commentId).orElseThrow(()-> new CommentNotFoundException(commentId));
        existingComment.setContent(requestDto.getContent());
        return mapper.toResponseDto(repository.save(existingComment));
    }

    public void deleteCommentById(UUID commentId) {
        if (!repository.existsById(commentId)){
            throw new CommentNotFoundException(commentId);
        }
        repository.deleteById(commentId);
    }

}

package com.beyefendisinemaci.beyefendisinemaci.comment.service;

import com.beyefendisinemaci.beyefendisinemaci.comment.dto.request.CommentRequestDto;
import com.beyefendisinemaci.beyefendisinemaci.comment.dto.response.CommentResponseDto;
import com.beyefendisinemaci.beyefendisinemaci.comment.entity.Comment;
import com.beyefendisinemaci.beyefendisinemaci.comment.exception.CommentNotFoundException;
import com.beyefendisinemaci.beyefendisinemaci.comment.mapper.CommentMapper;
import com.beyefendisinemaci.beyefendisinemaci.comment.repository.CommentRepository;
import com.beyefendisinemaci.beyefendisinemaci.movie.entity.Movie;
import com.beyefendisinemaci.beyefendisinemaci.movie.exception.MovieNotFoundException;
import com.beyefendisinemaci.beyefendisinemaci.movie.repository.MovieRepository;
import com.beyefendisinemaci.beyefendisinemaci.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentMapper mapper;
    private final CommentRepository commentRepository;
    private final MovieRepository movieRepository;

    @Transactional(readOnly = true)
    public Slice<CommentResponseDto> getCommentsByMovie(UUID id, Pageable pageable) {
        return commentRepository.findByMovieIdOrderByCreatedAtDesc(id, pageable).map(mapper::toResponseDto);
    }

    @Transactional(readOnly = true)
    public Slice<CommentResponseDto> getCommentsByUser(UUID id, Pageable pageable) {
        return commentRepository.findByUserIdOrderByCreatedAtDesc(id, pageable).map(mapper::toResponseDto);
    }

    @Transactional
    public CommentResponseDto addComment(UUID movieId, CommentRequestDto requestDto, User user) {
        Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new MovieNotFoundException(movieId));
        Comment comment = Comment.builder()
                .content(requestDto.getContent())
                .movie(movie)
                .user(user)
                .build();
        return mapper.toResponseDto(commentRepository.save(comment));
    }

    @Transactional
    public CommentResponseDto updateComment(UUID commentId, CommentRequestDto requestDto) {
        Comment existingComment = commentRepository.findById(commentId).orElseThrow(() -> new CommentNotFoundException(commentId));
        existingComment.setContent(requestDto.getContent());
        return mapper.toResponseDto(commentRepository.save(existingComment));
    }

    @Transactional
    public void deleteCommentById(UUID commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new CommentNotFoundException(commentId));
        commentRepository.delete(comment);
    }

    @Transactional
    public void deleteByUserId(UUID userId) {
        commentRepository.deleteByUserId(userId);
    }

    @Transactional
    public void deleteByMovieId(UUID movieId) {
        commentRepository.deleteByMovieId(movieId);
    }
}

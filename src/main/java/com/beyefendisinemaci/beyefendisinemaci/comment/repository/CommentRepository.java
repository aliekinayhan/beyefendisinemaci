package com.beyefendisinemaci.beyefendisinemaci.comment.repository;

import com.beyefendisinemaci.beyefendisinemaci.comment.entity.Comment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CommentRepository extends JpaRepository<Comment, UUID> {

    Slice<Comment> findByMovieId(UUID movieId, Pageable pageable);

    List<Comment> findByUserId(UUID userId);
}

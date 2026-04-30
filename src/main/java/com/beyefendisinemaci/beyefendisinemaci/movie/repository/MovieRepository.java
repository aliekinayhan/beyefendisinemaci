package com.beyefendisinemaci.beyefendisinemaci.movie.repository;

import com.beyefendisinemaci.beyefendisinemaci.movie.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MovieRepository extends JpaRepository<Movie, UUID> {
    /*
    SQL
    findBy -> where
    Title -> search in here
    Containing -> like %..%
    */
    List<Movie> findByTitleContainingIgnoreCase(String title);

    boolean existsByTmdbId(Integer tmdbId);
}
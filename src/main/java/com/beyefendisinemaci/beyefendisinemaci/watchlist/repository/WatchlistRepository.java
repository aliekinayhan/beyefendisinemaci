package com.beyefendisinemaci.beyefendisinemaci.watchlist.repository;

import com.beyefendisinemaci.beyefendisinemaci.watchlist.entity.Watchlist;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Repository
public interface WatchlistRepository extends JpaRepository<Watchlist, UUID> {
    Slice<Watchlist> findByUserId(UUID userId, Pageable pageable);

    boolean existsByUserIdAndMovieId(UUID userId, UUID movieId);

    @Transactional
    void deleteByUserIdAndMovieId(UUID userId, UUID movieId);
}

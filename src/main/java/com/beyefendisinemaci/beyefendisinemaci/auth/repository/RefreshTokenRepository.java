package com.beyefendisinemaci.beyefendisinemaci.auth.repository;

import com.beyefendisinemaci.beyefendisinemaci.auth.entity.RefreshToken;
import com.beyefendisinemaci.beyefendisinemaci.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {
    // Find by token instead of user, because if we searched by userId,
    // an attacker could send any userId to access other accounts.
    Optional<RefreshToken> findByToken(String token);
    @Transactional
    void deleteByUser(User user);
}

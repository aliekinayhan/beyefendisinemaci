package com.beyefendisinemaci.beyefendisinemaci.auth.entity;

import com.beyefendisinemaci.beyefendisinemaci.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "refresh_tokens")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    /* When we mark a column as unique, the DB needs to check every insert
       to ensure no duplicates exist, so it automatically adds an index
       to that column to perform this check efficiently. */
    @Column(unique = true)
    private String token;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @CreationTimestamp
    private LocalDateTime createdAt;
    private LocalDateTime expiry;
}

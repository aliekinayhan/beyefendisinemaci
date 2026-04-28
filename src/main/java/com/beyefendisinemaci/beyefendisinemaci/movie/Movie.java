package com.beyefendisinemaci.beyefendisinemaci.movie;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "movies")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String title;
    private String posterUrl;
    private String videoUrl;
    private String genre;
    private Integer releaseYear;
    private Integer tmdbId;

    @Column(columnDefinition = "TEXT")
    private String review;

    @CreationTimestamp
    private LocalDateTime createdAt;
}

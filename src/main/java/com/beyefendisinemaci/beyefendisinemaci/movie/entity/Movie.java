package com.beyefendisinemaci.beyefendisinemaci.movie.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
    private String shortVideoUrl;
    private String videoUrl;
    private String genre;
    private Integer releaseYear;
    private Integer tmdbId;
    @Column(columnDefinition = "TEXT")
    private String review;
    @CreationTimestamp
    private LocalDateTime createdAt;
}

package com.beyefendisinemaci.beyefendisinemaci.comment.dto.response;

import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentResponseDto {
    private UUID id;
    private String content;
    private LocalDateTime createdAt;
    private UUID movieId;
    // I took the user info via mapper
    private UUID userId;
    private String username;
    private String userProfilePhoto;
}

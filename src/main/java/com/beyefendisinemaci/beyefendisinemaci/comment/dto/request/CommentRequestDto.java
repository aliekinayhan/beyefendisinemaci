package com.beyefendisinemaci.beyefendisinemaci.comment.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentRequestDto {
    @NotBlank
    private String content;
    @NotNull
    private UUID movieId;
    @NotNull
    private UUID userId;
}

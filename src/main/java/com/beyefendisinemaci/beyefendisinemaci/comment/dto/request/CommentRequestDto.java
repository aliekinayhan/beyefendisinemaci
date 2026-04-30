package com.beyefendisinemaci.beyefendisinemaci.comment.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentRequestDto {
    @NotBlank
    private String content;
}

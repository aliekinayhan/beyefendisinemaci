package com.beyefendisinemaci.beyefendisinemaci.user.dto.response;

import com.beyefendisinemaci.beyefendisinemaci.user.entity.Role;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSearchResponseDto {
    private UUID id;
    private String username;
    private String profilePhoto;
    private Role role;
}

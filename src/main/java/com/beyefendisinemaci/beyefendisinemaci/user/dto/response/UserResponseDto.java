package com.beyefendisinemaci.beyefendisinemaci.user.dto.response;

import com.beyefendisinemaci.beyefendisinemaci.user.entity.Role;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDto {
    private UUID id;
    private String username;
    private String firstName;
    private String lastName;
    private String profilePhoto;
    private String coverPhoto;
    private Role role;
}

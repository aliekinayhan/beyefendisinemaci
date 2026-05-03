package com.beyefendisinemaci.beyefendisinemaci.user.dto.request;

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
public class UserUpdateRequest {
    private String username;
    private String firstName;
    private String lastName;
    private String profilePhoto;
    private String coverPhoto;

}

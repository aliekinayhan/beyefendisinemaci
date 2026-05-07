package com.beyefendisinemaci.beyefendisinemaci.user.dto.request;

import com.beyefendisinemaci.beyefendisinemaci.user.entity.Role;
import jakarta.validation.constraints.NotNull;
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
public class ChangeRoleRequest {
    @NotNull
    private Role role;
}

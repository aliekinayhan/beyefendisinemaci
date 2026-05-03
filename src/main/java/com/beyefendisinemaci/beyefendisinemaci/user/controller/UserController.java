package com.beyefendisinemaci.beyefendisinemaci.user.controller;

import com.beyefendisinemaci.beyefendisinemaci.user.dto.request.ChangePasswordRequest;
import com.beyefendisinemaci.beyefendisinemaci.user.dto.request.DeleteAccountRequest;
import com.beyefendisinemaci.beyefendisinemaci.user.dto.request.UserUpdateRequest;
import com.beyefendisinemaci.beyefendisinemaci.user.dto.response.UserResponseDto;
import com.beyefendisinemaci.beyefendisinemaci.user.entity.User;
import com.beyefendisinemaci.beyefendisinemaci.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @GetMapping("/users/{id}")
    public ResponseEntity<UserResponseDto> getProfile(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getProfile(id));
    }

    @GetMapping("/users/me")
    public ResponseEntity<UserResponseDto> getMyProfile(@AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(service.getProfile(currentUser.getId()));
    }

    @PutMapping("/users/me")
    public ResponseEntity<UserResponseDto> updateProfile(@Valid @RequestBody UserUpdateRequest request, @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(service.updateProfile(request, currentUser.getId()));
    }

    @PutMapping("/users/me/password")
    public ResponseEntity<Void> updatePassword(@Valid @RequestBody ChangePasswordRequest request, @AuthenticationPrincipal User currentUser) {
        service.changePassword(currentUser.getId(), request.getOldPassword(), request.getNewPassword());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/users/me")
    public ResponseEntity<Void> deleteProfile(@Valid @RequestBody DeleteAccountRequest request, @AuthenticationPrincipal User currentUser) {
        service.deleteAccount(currentUser.getId(), request.getPassword());
        return ResponseEntity.noContent().build();
    }


    // For Admin
    @DeleteMapping("/admin/users/{id}")
    public ResponseEntity<Void> deleteProfileAdmin(@PathVariable UUID id) {
        service.deleteAccountByAdmin(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/admin/users/{id}/freeze")
    public ResponseEntity<Void> freezeAccount(@PathVariable UUID id) {
        service.freezeAccount(id);
        return ResponseEntity.noContent().build();
    }


}

package com.beyefendisinemaci.beyefendisinemaci.user.service;

import com.beyefendisinemaci.beyefendisinemaci.user.dto.request.UserUpdateRequest;
import com.beyefendisinemaci.beyefendisinemaci.user.dto.response.UserResponseDto;
import com.beyefendisinemaci.beyefendisinemaci.user.entity.User;
import com.beyefendisinemaci.beyefendisinemaci.user.exception.PasswordIsIncorrectException;
import com.beyefendisinemaci.beyefendisinemaci.user.exception.UserNotFoundException;
import com.beyefendisinemaci.beyefendisinemaci.user.mapper.UserMapper;
import com.beyefendisinemaci.beyefendisinemaci.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final UserMapper mapper;
    private final PasswordEncoder encoder;

    public UserResponseDto getProfile(UUID userId) {
        User user = repository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        return mapper.toResponseDto(user);
    }

    //TODO: Check if username already exists
    public UserResponseDto updateProfile(UserUpdateRequest request, UUID userId) {
        User existingUser = repository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        existingUser.setUsername(request.getUsername());
        existingUser.setFirstName(request.getFirstName());
        existingUser.setLastName(request.getLastName());
        existingUser.setProfilePhoto(request.getProfilePhoto());
        existingUser.setCoverPhoto(request.getCoverPhoto());
        return mapper.toResponseDto(repository.save(existingUser));
    }

    public void changePassword(UUID userId, String oldPassword, String newPassword) {
        User user = repository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        if (encoder.matches(oldPassword, user.getPassword())) {
            user.setPassword(encoder.encode(newPassword));
            repository.save(user);
        } else {
            throw new PasswordIsIncorrectException();
        }
    }

    public void deleteAccount(UUID userId, String password) {
        User user = repository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        if (encoder.matches(password, user.getPassword())) {
            repository.deleteById(userId);
        } else {
            throw new PasswordIsIncorrectException();
        }
    }




    // For admin
    public void deleteAccountByAdmin(UUID userId) {
        User user = repository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        repository.delete(user);
    }

    public void freezeAccount(UUID userId) {
        User user = repository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        user.setEnabled(!user.isEnabled());
        repository.save(user);
    }

}

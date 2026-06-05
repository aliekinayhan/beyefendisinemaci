package com.beyefendisinemaci.beyefendisinemaci.user.service;

import com.beyefendisinemaci.beyefendisinemaci.auth.exception.UsernameAlreadyExistsException;
import com.beyefendisinemaci.beyefendisinemaci.auth.repository.RefreshTokenRepository;
import com.beyefendisinemaci.beyefendisinemaci.comment.service.CommentService;
import com.beyefendisinemaci.beyefendisinemaci.user.dto.request.UserUpdateRequest;
import com.beyefendisinemaci.beyefendisinemaci.user.dto.response.UserResponseDto;
import com.beyefendisinemaci.beyefendisinemaci.user.dto.response.UserSearchResponseDto;
import com.beyefendisinemaci.beyefendisinemaci.user.entity.Role;
import com.beyefendisinemaci.beyefendisinemaci.user.entity.User;
import com.beyefendisinemaci.beyefendisinemaci.user.exception.PasswordIsIncorrectException;
import com.beyefendisinemaci.beyefendisinemaci.user.exception.UserNotFoundException;
import com.beyefendisinemaci.beyefendisinemaci.user.mapper.UserMapper;
import com.beyefendisinemaci.beyefendisinemaci.user.repository.UserRepository;
import com.beyefendisinemaci.beyefendisinemaci.watchlist.service.WatchlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserMapper mapper;
    private final PasswordEncoder encoder;
    private final CommentService commentService;
    private final WatchlistService watchlistService;


    @Transactional(readOnly = true)
    public UserResponseDto getProfile(UUID userId) {
        User user = findByUserId(userId);
        return mapper.toResponseDto(user);
    }

    @Transactional
    public UserResponseDto updateProfile(UserUpdateRequest request, UUID userId) {
        User existingUser = findByUserId(userId);
        if (userRepository.existsByUsernameAndIdNot(request.getUsername(), userId))
            throw new UsernameAlreadyExistsException(request.getUsername());
        updateUser(existingUser, request);
        return mapper.toResponseDto(userRepository.save(existingUser));
    }

    @Transactional
    public void changePassword(UUID userId, String oldPassword, String newPassword) {
        User user = findByUserId(userId);
        verifyPassword(oldPassword, user.getPassword());
        user.setPassword(encoder.encode(newPassword));
        userRepository.save(user);
    }

    @Transactional
    public void deleteAccount(UUID userId, String password) {
        User user = findByUserId(userId);
        verifyPassword(password, user.getPassword());
        userDeletion(user);
    }

    // For admin
    @Transactional
    public void deleteAccountByAdmin(UUID userId) {
        User user = findByUserId(userId);
        userDeletion(user);
    }

    @Transactional
    public void freezeAccount(UUID userId) {
        User user = findByUserId(userId);
        user.setEnabled(!user.isEnabled());
        userRepository.save(user);
    }

    @Transactional
    public void changeRole(UUID userId, Role role) {
        User user = findByUserId(userId);
        user.setRole(role);
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public List<UserSearchResponseDto> searchUser(String username) {
        return userRepository.findByUsernameContainingIgnoreCase(username).stream()
                .map(mapper::toSearchResponseDto)
                .toList();
    }

    private void updateUser(User existingUser, UserUpdateRequest request) {
        existingUser.setUsername(request.getUsername());
        existingUser.setFirstName(request.getFirstName());
        existingUser.setLastName(request.getLastName());
        existingUser.setProfilePhoto(request.getProfilePhoto());
        existingUser.setCoverPhoto(request.getCoverPhoto());
    }

    private User findByUserId(UUID userId) {
        return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
    }

    private void verifyPassword(String rawPassword, String encodedPassword) {
        if (!encoder.matches(rawPassword, encodedPassword)) throw new PasswordIsIncorrectException();
    }

    private void userDeletion(User user) {
        refreshTokenRepository.deleteByUserId(user.getId());
        commentService.deleteByUserId(user.getId());
        watchlistService.deleteByUserId(user.getId());
        userRepository.delete(user);
    }
}

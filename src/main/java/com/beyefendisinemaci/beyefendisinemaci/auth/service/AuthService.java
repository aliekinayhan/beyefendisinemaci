package com.beyefendisinemaci.beyefendisinemaci.auth.service;

import com.beyefendisinemaci.beyefendisinemaci.auth.dto.request.LoginRequest;
import com.beyefendisinemaci.beyefendisinemaci.auth.dto.request.RefreshTokenRequest;
import com.beyefendisinemaci.beyefendisinemaci.auth.dto.request.RegisterRequest;
import com.beyefendisinemaci.beyefendisinemaci.auth.dto.response.AuthResponse;
import com.beyefendisinemaci.beyefendisinemaci.auth.entity.RefreshToken;
import com.beyefendisinemaci.beyefendisinemaci.auth.exception.EmailAlreadyExistsException;
import com.beyefendisinemaci.beyefendisinemaci.auth.exception.InvalidRefreshTokenException;
import com.beyefendisinemaci.beyefendisinemaci.auth.exception.RefreshTokenExpiredException;
import com.beyefendisinemaci.beyefendisinemaci.auth.exception.UsernameAlreadyExistsException;
import com.beyefendisinemaci.beyefendisinemaci.auth.repository.RefreshTokenRepository;
import com.beyefendisinemaci.beyefendisinemaci.config.JwtUtil;
import com.beyefendisinemaci.beyefendisinemaci.user.entity.Role;
import com.beyefendisinemaci.beyefendisinemaci.user.entity.User;
import com.beyefendisinemaci.beyefendisinemaci.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {
    // to check user from db
    private final UserRepository userRepository;
    // to check refresh token from db
    private final RefreshTokenRepository refreshTokenRepository;
    // to hash password
    private final PasswordEncoder passwordEncoder;
    // to create and control jwt
    private final JwtUtil jwtUtil;
    // this is the mechanism of spring security to validate login
    private final AuthenticationManager authenticationManager;


    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername()))
            throw new UsernameAlreadyExistsException(request.getUsername());
        if (userRepository.existsByEmail(request.getEmail()))
            throw new EmailAlreadyExistsException(request.getEmail());
        User user = User.builder()
                .username(request.getUsername())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .enabled(true)
                .build();
        userRepository.save(user);
        return generateAuthResponse(user);
    }

    public AuthResponse login(LoginRequest request) {
        // authenticationManager does not return User object
        authenticationManager.authenticate(
                // a simple wrapper that carries username and password to the AuthenticationManager
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                ));
        // We need user object to produce token
        User user = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new UsernameNotFoundException("User not found: " + request.getUsername()));
        // delete existing refresh token to prevent reuse; note: this logs out other devices
        refreshTokenRepository.deleteByUser(user);
        return generateAuthResponse(user);
    }

    public void logout(String refreshToken) {
        RefreshToken token = refreshTokenRepository.findByToken(refreshToken).orElseThrow(InvalidRefreshTokenException::new);
        refreshTokenRepository.delete(token);
    }

    public AuthResponse refreshToken(RefreshTokenRequest request) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(request.getRefreshToken()).orElseThrow(InvalidRefreshTokenException::new);
        if (refreshToken.getExpiry().isBefore(LocalDateTime.now())) {
            refreshTokenRepository.delete(refreshToken);
            throw new RefreshTokenExpiredException();
        }
        User user = refreshToken.getUser();
        String newAccessToken = jwtUtil.generateToken(user);
        return AuthResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(request.getRefreshToken())
                .username(user.getUsername())
                .build();
    }

    // at login and register method, these codes were repeating
    private AuthResponse generateAuthResponse(User user) {
        String accessToken = jwtUtil.generateToken(user);
        // we need a value that is unique and unpredictable
        String refreshTokenValue = UUID.randomUUID().toString();
        RefreshToken refreshToken = RefreshToken.builder()
                .token(refreshTokenValue)
                .user(user)
                .expiry(LocalDateTime.now().plusDays(7))
                .build();
        refreshTokenRepository.save(refreshToken);
        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshTokenValue)
                .username(user.getUsername())
                .build();
    }

}


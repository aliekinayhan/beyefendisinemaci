package com.beyefendisinemaci.beyefendisinemaci.auth.service;

import com.beyefendisinemaci.beyefendisinemaci.auth.repository.RefreshTokenRepository;
import com.beyefendisinemaci.beyefendisinemaci.config.JwtUtil;
import com.beyefendisinemaci.beyefendisinemaci.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
}

package com.beyefendisinemaci.beyefendisinemaci.config;

import com.beyefendisinemaci.beyefendisinemaci.user.service.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl service;

    // even if we don't use response directly, filterChain.doFilter() requires it
    // filters can also use it to stop the request and send an error (e.g. 401) directly
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            // Some endpoints are public
            filterChain.doFilter(request, response);
            return;
        }
        try {
            final String token = authHeader.substring(7);
            final String username = jwtUtil.extractUsername(token);
            // each request runs in its own thread with its own SecurityContextHolder
            // skip authentication if already set; another filter may have already authenticated this request
            // so at the beginning of every http request SecurityContextHolder is null
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = service.loadUserByUsername(username);
                if (jwtUtil.isTokenValid(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        } catch (Exception e) {
            // empty SecurityContextHolder will cause Spring Security to return 401
            // don't expose error details to attacker
            return;
        }
        filterChain.doFilter(request, response);
    }
}

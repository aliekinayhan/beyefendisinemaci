package com.beyefendisinemaci.beyefendisinemaci.config.ratelimit;

import com.beyefendisinemaci.beyefendisinemaci.jwt.JwtUtil;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class PublicMovieEndpointRateLimit extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private static final String UUID_PATTERN = "/api/movies/[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}";

    private final Cache<String, Bucket> ipBuckets = Caffeine.newBuilder()
            .expireAfterAccess(1, TimeUnit.HOURS)
            .maximumSize(10000)
            .build();

    private Bucket createIpBucket() {
        Bandwidth limit = Bandwidth.builder()
                .capacity(12)
                .refillGreedy(12, Duration.ofMinutes(1))
                .build();
        return Bucket.builder()
                .addLimit(limit)
                .build();
    }

    private Bucket getIpBucket(String ip) {
        return ipBuckets.get(ip, k -> createIpBucket());
    }

    private boolean isJwtValid(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return false;
        }
        try {
            String token = authHeader.substring(7);
            jwtUtil.extractUsername(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String path = request.getRequestURI();
        if (path.equals("/api/movies/recent") || path.matches(UUID_PATTERN)) {

            if (isJwtValid(request)) {
                filterChain.doFilter(request, response);
                return;
            }

            String ip = request.getHeader("X-Forwarded-For");

            if (ip == null || ip.isBlank()) {
                ip = request.getRemoteAddr();
            }

            Bucket bucket = getIpBucket(ip);

            if (!bucket.tryConsume(1)) {
                response.setStatus(429);
                response.setContentType("application/json");
                response.getWriter().write("{\"message\": \"Too many requests. Please try again later.\"}");
                return;
            }
        }
        filterChain.doFilter(request, response);
    }
}

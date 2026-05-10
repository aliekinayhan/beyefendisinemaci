package com.beyefendisinemaci.beyefendisinemaci.config.ratelimit;

import com.beyefendisinemaci.beyefendisinemaci.user.entity.User;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Component
public class S3RateLimitFilter extends OncePerRequestFilter {
    private static final String PROFILE_PHOTO = "/api/s3/upload/profile-photo";
    private static final String COVER_PHOTO = "/api/s3/upload/cover-photo";

    private final Cache<String, Bucket> userBuckets = Caffeine.newBuilder()
            .expireAfterAccess(7, TimeUnit.DAYS)
            .maximumSize(10000)
            .build();

    private Bucket createUserBucket() {
        Bandwidth limit = Bandwidth.builder()
                .capacity(1)
                .refillGreedy(1, Duration.ofDays(7))
                .build();
        return Bucket.builder()
                .addLimit(limit)
                .build();
    }

    private Bucket getUserBucket(String userId) {
        return userBuckets.get(userId, k -> createUserBucket());
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String path = request.getRequestURI();

        if (!path.equals(PROFILE_PHOTO) && !path.equals(COVER_PHOTO)) {
            filterChain.doFilter(request, response);
            return;
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated() || !(auth.getPrincipal() instanceof User user)) {
            filterChain.doFilter(request, response);
            return;
        }

        // Admin has no limit
        if (user.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN"))) {
            filterChain.doFilter(request, response);
            return;
        }

        String userId = user.getId().toString();
        Bucket bucket = getUserBucket(userId);

        if (!bucket.tryConsume(1)) {
            response.setStatus(429);
            response.setContentType("application/json");
            response.getWriter().write("{\"message\": \"You can only upload once per week.\"}");
            return;
        }

        filterChain.doFilter(request, response);
    }
}

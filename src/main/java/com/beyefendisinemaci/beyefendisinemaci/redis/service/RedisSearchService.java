package com.beyefendisinemaci.beyefendisinemaci.redis.service;

import com.beyefendisinemaci.beyefendisinemaci.movie.dto.response.MovieResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisSearchService {
    private final RedisTemplate<String, Object> redisTemplate;

    private static final String SEARCH_CACHE_PREFIX = "search:";

    public void cacheSearchResult(String query, List<MovieResponseDto> results) {
        String key = SEARCH_CACHE_PREFIX + query.toLowerCase().trim();
        redisTemplate.opsForValue().set(key, results, 1, TimeUnit.HOURS);
    }

    @SuppressWarnings("unchecked")
    public List<MovieResponseDto> getCachedSearchResult(String query) {
        String key = SEARCH_CACHE_PREFIX + query.toLowerCase().trim();
        return (List<MovieResponseDto>) redisTemplate.opsForValue().get(key);
    }

    public void evictAllSearchCache() {
        Set<String> keys = redisTemplate.keys(SEARCH_CACHE_PREFIX + "*");
        if (!keys.isEmpty()) {
            redisTemplate.delete(keys);
        }
    }
}
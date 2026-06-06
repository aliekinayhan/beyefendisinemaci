package com.beyefendisinemaci.beyefendisinemaci.redis.service;

import com.beyefendisinemaci.beyefendisinemaci.movie.dto.response.MovieResponseDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class RedisSearchService {

    private final RedisTemplate<String, List<MovieResponseDto>> movieSearchRedisTemplate;

    public RedisSearchService(
            @Qualifier("movieSearchRedisTemplate")
            RedisTemplate<String, List<MovieResponseDto>> movieSearchRedisTemplate) {
        this.movieSearchRedisTemplate = movieSearchRedisTemplate;
    }

    private static final String SEARCH_CACHE_PREFIX = "search:";

    public void cacheSearchResult(String query, List<MovieResponseDto> results) {
        String key = SEARCH_CACHE_PREFIX + query.toLowerCase().trim();
        movieSearchRedisTemplate.opsForValue().set(key, results, 1, TimeUnit.HOURS);
    }

    public List<MovieResponseDto> getCachedSearchResult(String query) {
        String key = SEARCH_CACHE_PREFIX + query.toLowerCase().trim();
        return movieSearchRedisTemplate.opsForValue().get(key);
    }

    public void evictAllSearchCache() {
        Set<String> keys = movieSearchRedisTemplate.keys(SEARCH_CACHE_PREFIX + "*");
        if (keys != null && !keys.isEmpty()) {
            movieSearchRedisTemplate.delete(keys);
        }
    }
}
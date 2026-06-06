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

    private final RedisTemplate<String, String> redisTemplate;
    private final RedisTemplate<String, Object> objectRedisTemplate;

    private static final String KEY = "trending:searches";
    private static final String SEARCH_CACHE_PREFIX = "movie-search:";

    public void recordSearch(String query) {
        String term = query.toLowerCase().trim();
        redisTemplate.opsForZSet().incrementScore(KEY, term, 1);
        redisTemplate.expire(KEY, 7, TimeUnit.DAYS);
    }

    public List<String> getTrendingSearches() {
        Set<String> results = redisTemplate.opsForZSet().reverseRange(KEY, 0, 9);
        return results != null ? new ArrayList<>(results) : new ArrayList<>();
    }

    public void cacheSearchResult(String query, List<MovieResponseDto> results) {
        String key = SEARCH_CACHE_PREFIX + query.toLowerCase().trim();
        objectRedisTemplate.opsForValue().set(key, results, 1, TimeUnit.HOURS);
    }

    @SuppressWarnings("unchecked")
    public List<MovieResponseDto> getCachedSearchResult(String query) {
        String key = SEARCH_CACHE_PREFIX + query.toLowerCase().trim();
        return (List<MovieResponseDto>) objectRedisTemplate.opsForValue().get(key);
    }

    public void evictAllSearchCache() {
        Set<String> keys = objectRedisTemplate.keys(SEARCH_CACHE_PREFIX + "*");
        if (keys != null && !keys.isEmpty()) {
            objectRedisTemplate.delete(keys);
        }
    }
}
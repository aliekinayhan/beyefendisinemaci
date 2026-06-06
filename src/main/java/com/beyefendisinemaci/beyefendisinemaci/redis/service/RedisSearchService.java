package com.beyefendisinemaci.beyefendisinemaci.redis.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RedisSearchService {

    private final RedisTemplate<String, String> redisTemplate;

    private static final String KEY = "trending:searches";

    public void recordSearch(String query) {
        redisTemplate.opsForZSet().incrementScore(KEY, query.toLowerCase().trim(), 1);
    }

    public List<String> getTrendingSearches() {
        Set<String> results = redisTemplate.opsForZSet().reverseRange(KEY, 0, 9);
        return results != null ? new ArrayList<>(results) : new ArrayList<>();
    }
}
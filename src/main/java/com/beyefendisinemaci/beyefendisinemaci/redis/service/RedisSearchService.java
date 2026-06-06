package com.beyefendisinemaci.beyefendisinemaci.redis.service;

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

    private static final String KEY = "trending:searches";

    public void recordSearch(String query) {
        String term = query.toLowerCase().trim();
        redisTemplate.opsForZSet().incrementScore(KEY, term, 1);
        redisTemplate.expire(KEY, 7, TimeUnit.DAYS);
    }

    public List<String> getTrendingSearches() {
        Set<String> results = redisTemplate.opsForZSet().reverseRange(KEY, 0, 9);
        return results != null ? new ArrayList<>(results) : new ArrayList<>();
    }
}
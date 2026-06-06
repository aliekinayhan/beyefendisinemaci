package com.beyefendisinemaci.beyefendisinemaci.redis.config;

import com.beyefendisinemaci.beyefendisinemaci.movie.dto.response.MovieResponseDto;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

@Configuration
public class RedisConfig {

    @Primary
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setKeySerializer(new StringRedisSerializer());

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.activateDefaultTyping(
                objectMapper.getPolymorphicTypeValidator(),
                ObjectMapper.DefaultTyping.NON_FINAL
        );
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        GenericJackson2JsonRedisSerializer gjs = new GenericJackson2JsonRedisSerializer(objectMapper);
        template.setValueSerializer(gjs);
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(gjs);
        template.afterPropertiesSet();
        return template;
    }

    @Bean
    public RedisTemplate<String, List<MovieResponseDto>> movieSearchRedisTemplate(
            RedisConnectionFactory connectionFactory) {

        RedisTemplate<String, List<MovieResponseDto>> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setKeySerializer(new StringRedisSerializer());

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        JavaType type = mapper.getTypeFactory()
                .constructCollectionType(List.class, MovieResponseDto.class);
        Jackson2JsonRedisSerializer<List<MovieResponseDto>> serializer =
                new Jackson2JsonRedisSerializer<>(mapper, type);

        template.setValueSerializer(serializer);
        template.setHashValueSerializer(serializer);
        template.afterPropertiesSet();
        return template;
    }
}

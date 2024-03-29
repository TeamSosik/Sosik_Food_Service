package com.example.sosikfoodservice.config;

import com.example.sosikfoodservice.repository.redis.CacheFood;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisKeyValueAdapter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableRedisRepositories(
        enableKeyspaceEvents =
                RedisKeyValueAdapter
                        .EnableKeyspaceEvents.ON_STARTUP // ttl에 의해 관련키(Indexed로 생성된) 모두 삭제된다.
)
public class RedisConfig {

    @Value(value = "${spring.data.redis.host}")
    private String host;
    @Value(value = "${spring.data.redis.port}")
    private int port;
    @Value(value = "${spring.data.redis.password}")
    private String password;

    // redisTemplate 생성
    @Bean
    public RedisTemplate<String, CacheFood> redisTemplate() {

        RedisTemplate<String, CacheFood> redisTemplate = new RedisTemplate<>();

        redisTemplate.setConnectionFactory(redisConnectionFactory());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(
                new Jackson2JsonRedisSerializer<CacheFood>(CacheFood.class)
        );

        return redisTemplate;
    }
    // redisFactory 생성
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {

        LettuceConnectionFactory lettuceConnectionFactory =
                new LettuceConnectionFactory(host, port);
        lettuceConnectionFactory.setPassword(password);

        return lettuceConnectionFactory;
    }

    @Bean
    public ListOperations<String, String> listOperations(RedisTemplate<String, String> redisTemplate) {
        return redisTemplate.opsForList();
    }


}

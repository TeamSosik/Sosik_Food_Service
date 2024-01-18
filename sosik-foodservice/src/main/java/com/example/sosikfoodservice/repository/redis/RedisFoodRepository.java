package com.example.sosikfoodservice.repository.redis;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RedisFoodRepository extends CrudRepository<RedisFood, Long> {

    List<RedisFood> findAll();

    List<RedisFood> findTop10ByNameContainingIgnoreCase(String inputValue);
}

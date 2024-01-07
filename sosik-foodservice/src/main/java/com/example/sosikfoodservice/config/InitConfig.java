package com.example.sosikfoodservice.config;

import com.example.sosikfoodservice.model.entity.FoodEntity;
import com.example.sosikfoodservice.repository.FoodRepository;
import com.example.sosikfoodservice.repository.redis.RedisFood;
import com.example.sosikfoodservice.repository.redis.RedisFoodRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * 설명 : 음식데이터를 레디스에 담습니다.
 *       - 서버가 시작될 때 실행됩니다.
 */
@RequiredArgsConstructor
@Component
public class InitConfig {

    private final RedisFoodRepository redisFoodRepository;
    private final FoodRepository foodRepository;

    @PostConstruct
    private void init() {

        List<FoodEntity> foodList = foodRepository.findAll();

        List<RedisFood> redisFoodList = foodList.stream()
                .map(RedisFood::create)
                .collect(toList());

        redisFoodRepository.saveAll(redisFoodList);
    }



}

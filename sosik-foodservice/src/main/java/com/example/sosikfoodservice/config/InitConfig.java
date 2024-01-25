package com.example.sosikfoodservice.config;

import com.example.sosikfoodservice.model.entity.FoodEntity;
import com.example.sosikfoodservice.repository.FoodRepository;
import com.example.sosikfoodservice.repository.redis.CacheFood;
import com.example.sosikfoodservice.repository.redis.RedisFoodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

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
    private final long executionTime = 1000 * 60 * 28;// 28분마다 한번씩 실행

    @Scheduled(fixedRate = executionTime)
    private void init() {

        List<FoodEntity> foodList = foodRepository.findAll();

        List<CacheFood> redisFoodList = foodList.stream()
                .map(CacheFood::create)
                .collect(toList());

        redisFoodRepository.saveAll(redisFoodList);
    }



}

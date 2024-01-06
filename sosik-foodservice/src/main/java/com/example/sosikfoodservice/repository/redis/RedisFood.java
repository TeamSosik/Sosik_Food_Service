package com.example.sosikfoodservice.repository.redis;

import com.example.sosikfoodservice.model.entity.FoodEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.math.BigDecimal;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@RedisHash(
        value = "redisFood",
        timeToLive = 60 * 30 // 30분
)
public class RedisFood {

    @Id
    private Long foodId;
    @Indexed
    private String name;
    private BigDecimal carbo;
    private BigDecimal protein;
    private BigDecimal fat;
    private BigDecimal kcal;
    private BigDecimal size;

    @Builder
    public RedisFood(
            Long foodId,
            String name,
            BigDecimal carbo,
            BigDecimal protein,
            BigDecimal fat,
            BigDecimal kcal,
            BigDecimal size
    ) {
        this.foodId = foodId;
        this.name = name;
        this.carbo = carbo;
        this.protein = protein;
        this.fat = fat;
        this.kcal = kcal;
        this.size = size;
    }

    public static RedisFood create(FoodEntity foodEntity) {

        return RedisFood.builder()
                .foodId(foodEntity.getFoodId())
                .name(foodEntity.getName())
                .carbo(foodEntity.getCarbo())
                .protein(foodEntity.getProtein())
                .fat(foodEntity.getFat())
                .kcal(foodEntity.getKcal())
                .size(foodEntity.getSize())
                .build();
    }
}

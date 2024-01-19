package com.example.sosikfoodservice.dto.response;

import com.example.sosikfoodservice.model.entity.FoodEntity;
import com.example.sosikfoodservice.repository.redis.RedisFood;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class ResponseGetFood {

    private Long foodId;
    private String name;
    private BigDecimal carbo;
    private BigDecimal protein;
    private BigDecimal fat;
    private BigDecimal kcal;
    private BigDecimal size;
    private String createdBy; // 생성자
    private String modifiedBy;//수정자
    private LocalDateTime createdAt; // 생성일시
    private LocalDateTime modifiedAt; //수정일시

    @Builder
    public ResponseGetFood(Long foodId, String name, BigDecimal carbo, BigDecimal protein, BigDecimal fat, BigDecimal kcal, BigDecimal size, String createdBy, String modifiedBy, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.foodId = foodId;
        this.name = name;
        this.carbo = carbo;
        this.protein = protein;
        this.fat = fat;
        this.kcal = kcal;
        this.size = size;
        this.createdBy = createdBy;
        this.modifiedBy = modifiedBy;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public static ResponseGetFood create(FoodEntity foodEntity) {

        return ResponseGetFood.builder()
                .foodId(foodEntity.getFoodId())
                .name(foodEntity.getName())
                .carbo(foodEntity.getCarbo())
                .protein(foodEntity.getProtein())
                .fat(foodEntity.getFat())
                .kcal(foodEntity.getKcal())
                .size(foodEntity.getSize())
                .createdBy(foodEntity.getCreatedBy())
                .modifiedBy(foodEntity.getModifiedBy())
                .createdAt(foodEntity.getCreatedAt())
                .modifiedAt(foodEntity.getModifiedAt())
                .build();
    }

    public static ResponseGetFood create(RedisFood redisFood) {

        return ResponseGetFood.builder()
                .foodId(redisFood.getFoodId())
                .name(redisFood.getName())
                .carbo(redisFood.getCarbo())
                .protein(redisFood.getProtein())
                .fat(redisFood.getFat())
                .kcal(redisFood.getKcal())
                .size(redisFood.getSize())
                .createdBy(redisFood.getCreatedBy())
                .modifiedBy(redisFood.getModifiedBy())
                .createdAt(redisFood.getCreatedAt())
                .modifiedAt(redisFood.getModifiedAt())
                .build();
    }
}
